package thewall.engine;

import io.github.alexarchambault.windowsansi.WindowsAnsi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFWErrorCallback;
import thewall.engine.twilight.Application;
import thewall.engine.twilight.Node;
import thewall.engine.twilight.ViewPort;
import thewall.engine.twilight.audio.SoundMaster;
import thewall.engine.twilight.debugger.TEngineDebugger;
import thewall.engine.twilight.debugger.console.DebugConsole;
import thewall.engine.twilight.errors.InitializationException;
import thewall.engine.twilight.events.EventManager;
import thewall.engine.twilight.events.JTEEventManager;
import thewall.engine.twilight.hardware.Hardware;
import thewall.engine.twilight.hardware.PlatformEnum;
import thewall.engine.twilight.input.Input;
import thewall.engine.twilight.runtime.AbstractRuntime;
import thewall.engine.twilight.runtime.TwilightRuntimeService;
import thewall.engine.twilight.runtime.app.JTEEnvironment;
import thewall.engine.twilight.system.AppSettings;
import thewall.engine.twilight.system.JTESystem;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static thewall.engine.twilight.utils.Validation.checkNull;


public abstract class LegacyApp implements Application {
    private final static PlatformEnum[] supportedPlatform = {PlatformEnum.WINDOWS, PlatformEnum.LINUX, PlatformEnum.MACOS};
    private final static Logger logger = LogManager.getLogger(LegacyApp.class);
    private final Hardware hardware = JTESystem.createBestHardware();
    private static AtomicBoolean isInit = new AtomicBoolean(false);
    private int fps = 0;
    public final Node rootNode = new Node();
    private EventManager eventManager;
    public final ViewPort guiViewPort;
    public final ViewPort viewPort;

    private AppSettings appSettings;

    private SoundMaster sound;
    private Input input;

    public LegacyApp(){
        this.viewPort = new ViewPort();
        this.guiViewPort = new ViewPort();
        this.appSettings = new AppSettings();
    }

    public void setName(String name){
        checkNull();
        this.appSettings.setTitle(name);
    }

    public void setSettings(AppSettings settings){
        checkNull(settings);
        this.appSettings = settings;
    }

    @Override
    public void setEventManager(EventManager eventManager){
        if(this.eventManager != null){
            throw new IllegalStateException("Event manager is already set.");
        }
        checkNull(eventManager);
        this.eventManager = eventManager;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public AppSettings getSettings() {
        return appSettings;
    }

    @Override
    public void onInit() {
        viewPort.attachScene(rootNode);

        init();
    }

    @Override
    public Hardware getHardware() {
        return hardware;
    }

    @Override
    public Input input() {
        return input;
    }

    @Override
    public void setInput(Input input) {
        checkNull(input);
        this.input = input;
    }


    @Override
    public void setSound(SoundMaster sound) {
        checkNull(sound);
        this.sound = sound;
    }

    private long lastUpdate = System.currentTimeMillis();
    private int frames;
    @Override
    public void update() {
        frames++;
        long endTime = System.currentTimeMillis();
        if(endTime - lastUpdate > 1000){
            fps = frames;
            frames = 0;
            lastUpdate = System.currentTimeMillis();
        }
        onUpdate();
    }

    @Override
    public ViewPort getGUIViewPort() {
        return guiViewPort;
    }

    @Override
    public ViewPort getViewPort() {
        return viewPort;
    }

    @Override
    public SoundMaster getSound() {
        return sound;
    }

    private volatile Throwable error;
    private volatile boolean isError = false;

    private @Nullable AbstractRuntime<LegacyApp> findRuntime(){
        int timeout = 25000;

        AbstractRuntime<LegacyApp> runtime;
        runtime = TwilightRuntimeService.findRuntime(LegacyApp.class);
        if(runtime == null){
            throw new RuntimeException("No runtime found, did the app is initialized?");
        }


        logger.info("Found runtime [" + runtime.getClass().getName() + "], executing program...");
        Thread thread = new Thread(() -> runtime.execute(this));
        thread.setName("TwLight Main Thread");

        logger.debug("Runtime thread exception guard is ready");
        thread.setUncaughtExceptionHandler((t, e) -> {
            logger.error(String.format("[%s] Thread error", t), e);
            isError = true;
            error = e;
        });

        logger.debug("Starting runtime thread");
        thread.start();

        long start = System.currentTimeMillis();

        logger.debug("Waiting for runtime to continue");
        while (!runtime.isReady()){
            if(isError){
                logger.fatal("Unexpected Twilight runtime error, aborting. [" +  error.toString() + "]");
                if(!thread.isAlive()){
                    logger.debug("Thread is alive, interrupting...");
                    thread.interrupt();
                }
                return null;
            }

            if((System.currentTimeMillis() - start) >= timeout){
                logger.fatal("Runtime initialize timeout, aborting...");
                return null;
            }

            Thread.onSpinWait();
        }

        logger.info("Runtime ready to work, initialization complet");

        thread.setUncaughtExceptionHandler(null);


        return runtime;
    }


    private void startApp(){
        AbstractRuntime<LegacyApp> runtime;
        logger.info("Starting application, searching for runtime...");
        logger.debug("Finding runtime to run app");
        try {
            runtime = findRuntime();
            if(runtime == null){
                logger.fatal("Engine was not initialized probably, runtime is null");
                System.exit(-1);
            }
        }catch (Exception e){
            logger.fatal("Startup failure", e);
            System.exit(1);
        }

        //app.runtime = runtime;
    }

    public static String getVersion(){
        return JTESystem.name;
    }

    public void start(){
        if(!isInit.get()){
            logger.info("Initializing Twilight " + JTESystem.name);
            logger.debug("Warming up JVM...");

            class Dummy {
                public void m() {
                }
            }
            for (int i = 0; i < 10000000; i++) {
                Dummy dummy = new Dummy();
                dummy.m();
            }

            logger.debug("Installing ANSI Console");
            AnsiConsole.systemInstall();

            if (com.oracle.svm.core.os.IsDefined.WIN32()) {
                logger.debug("WIN32 WindowsANSI setting up");
                try {
                    WindowsAnsi.setup();
                } catch (IOException e) {
                    logger.warn("ANSI terminal colors unavailable to init", e);
                }

            }

            logger.debug("Setting ANSI console out OutputStream [" + AnsiConsole.out().toString() + "]" );
            logger.debug("Setting ANSI console err OutputStream [" + AnsiConsole.err().toString() + "]" );
            System.setOut(AnsiConsole.out());
            System.setErr(AnsiConsole.err());

            logger.debug("Creating GLFW error callback");
            GLFWErrorCallback.createPrint(System.err).set();

            System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

            glfwSetErrorCallback((error1, description) -> {
                logger.error("GLFW Error [" + error1 + "]: " + description);
            });

            boolean isSupported = false;
            for(PlatformEnum platform : supportedPlatform){
                if(platform ==  hardware.getPlatform()){
                    isSupported = true;
                    break;
                }
            }

            if(!isSupported){
                logger.fatal("Unsupported platform [" + hardware.getPlatform().getName() + "]", new InitializationException("Engine", "Unsp"));
            }

            logger.debug("Setting stream proxy JTE debug console");
            TEngineDebugger.setPrintProxyDebugger(DebugConsole.getConsole());

            logger.debug("Starting Twilight Runtime Service");
            TwilightRuntimeService.init();

            if(isInit.compareAndSet(false, true)) {
                TwilightRuntimeService.registerRuntime(LegacyApp.class, JTEEnvironment.class);
                logger.debug("Registering runtime service");
            }

            if(!glfwInit()){
                throw new InitializationException("Engine", "OpenGL GLFW init failed");
            }

            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);

            isInit.set(true);
        }

        startApp();
    }

    public final int getFPS(){
        return fps;
    }

    protected abstract void init();

    protected abstract void onUpdate();
}

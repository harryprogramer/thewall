package thewall.engine.twilight;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import thewall.engine.twilight.audio.SoundMaster;
import thewall.engine.twilight.debugger.TEngineDebugger;
import thewall.engine.twilight.debugger.console.DebugConsole;
import thewall.engine.twilight.display.DisplayResizeCallback;
import thewall.engine.twilight.display.GLFWWindowManager;
import thewall.engine.twilight.entity.Entity;
import thewall.engine.twilight.entity.Light;
import thewall.engine.twilight.errors.InitializationException;
import thewall.engine.twilight.events.EventManager;
import thewall.engine.twilight.events.TEventManager;
import thewall.engine.twilight.hardware.Hardware;
import thewall.engine.twilight.hardware.PlatformEnum;
import thewall.engine.twilight.hardware.hna.RealtimeHNAccess;
import thewall.engine.twilight.input.InputProvider;
import thewall.engine.twilight.input.keyboard.Keyboard;
import thewall.engine.twilight.input.keyboard.TGLFWKeyboard;
import thewall.engine.twilight.input.keyboard.TKeyboardCallback;
import thewall.engine.twilight.input.keyboard.KeyboardKeys;
import thewall.engine.twilight.input.mouse.Mouse;
import thewall.engine.twilight.input.mouse.TGLFWMouse;
import thewall.engine.twilight.models.Loader;
import thewall.engine.twilight.render.MasterRenderer;
import thewall.engine.twilight.runtime.AbstractRuntime;
import thewall.engine.twilight.runtime.app.TEngineAppRuntime;
import thewall.engine.twilight.runtime.TwilightRuntimeService;
import thewall.engine.twilight.terrain.Terrain;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public abstract class TwilightApp extends GLFWWindowManager {
    private static final AtomicBoolean isInit = new AtomicBoolean(false);
    private final static Logger logger = LogManager.getLogger(TwilightApp.class);
    private final static PlatformEnum[] supportedPlatform = {PlatformEnum.WINDOWS, PlatformEnum.LINUX, PlatformEnum.MACOS};
    private volatile boolean isStackTraceEnabled = false;
    private volatile AbstractRuntime<TwilightApp> runtime;
    private static double previousTime = glfwGetTime();
    private final static int initTimeout = 15;
    private static long timeout = 15000;
    private PrintWriter logCallback;
    private Thread renderThread;

    @Getter
    public static final String version = "1.0.6";

    @Getter
    @Setter
    private DebugConsole debugConsole = DebugConsole.getConsole();

    @Getter
    private volatile int frameLimit = 60; // DEFAULT FRAME LIMIT

    private static final Hardware hnaAccess = new RealtimeHNAccess();
    private static final Hardware asyncHNAccess = null; // TODO

    @Getter
    private String name = "App";

    @Getter
    private Light light = new Light(new Vector3f(0, 0, 0) ,new Vector3f(0, 0, 0));
    //@Getter
    //private Camera rndrCamera = new Camera();

    @Getter
    private final Loader loader = new Loader();


    @Getter
    @Setter
    private volatile SoundMaster soundMaster;

    @Getter
    private int windowWidth = 1280, windowHeight = 720;

    @Getter
    @Setter
    private volatile MasterRenderer renderer;

    @Getter
    private final EventManager eventManager = new TEventManager();

    private volatile Keyboard keyboard;
    private volatile Mouse mouse;
    private InputProvider input;

    public InputProvider input(){
        checkInit();
        checkStart();
        return input;
    }

    /**
     * Set window size
     *
     * @param width width of display
     * @param height height of display
     *  */
    public void setWindowSize(int width, int height){
        windowHeight = height;
        windowWidth = width;
    }


    /**
     * Set window title
     *
     * @param windowTitle window title
     * */
    public void setWindowTitle(String windowTitle){
        checkInit();
        glfwSetWindowTitle(getWindowPointer(), windowTitle);
    }

    public Hardware getRealtimeHardware(){
        return hnaAccess;
    }

    public Hardware getIndexedAsyncHardware(){
        return asyncHNAccess;
    }

    public void enableVSync(){
        checkInit();
        super.enableVSync();
    }

    public void disableVSync(){
        checkInit();
        super.disableVSync();
    }

    public void setLogCallback(PrintWriter callback){
        logCallback = callback;
    }

    public void setKeyboardCallback(TKeyboardCallback TKeyboardCallback){
        checkInit();
        runtime.executeTask(() -> glfwSetKeyCallback(getWindowPointer(), (window, key, scancode, action, mods) -> {
            try {
                TKeyboardCallback.invoke(KeyboardKeys.keyToEnum(key), scancode, action, mods);
            }catch (Exception e){
                //logger.warn("Keyboard callback error " + e.getMessage());
            }
        })
        );
    }

    public void processTerrain(Terrain terrain){
        renderer.processTerrain(terrain);
    }

    public void processEntity(Entity entity){
        renderer.processEntity(entity);
    }

    public void processLight(Light light){
        this.light = light;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void enableDebugStackTrace(){
        isStackTraceEnabled = true;
    }

    public void disableDebugStackTrace(){
        isStackTraceEnabled = false;
    }

    //public void rndrProcessCamera(Camera camera){
    //    this.rndrCamera = camera;
    //}


    @SneakyThrows
    public static void init(){

        class Dummy {
            public void m() {
            }
        }
        for (int i = 0; i < 10000000; i++) {
            Dummy dummy = new Dummy();
            dummy.m();
        }

        System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");

        logger.info("Initializing TEngine " + getVersion());

        boolean isSupported = false;
        for(PlatformEnum platform : supportedPlatform){
            if(platform ==  hnaAccess.getPlatform()){
                isSupported = true;
                break;
            }
        }

        if(!isSupported){
            logger.fatal("Unsupported platform [" + hnaAccess.getPlatform().getName() + "]", new InitializationException("Engine", "Unsp"));
        }

        TEngineDebugger.setPrintProxyDebugger(DebugConsole.getConsole());

        if(isInit.compareAndSet(false, true)) {
            TwilightRuntimeService.registerRuntime(TwilightApp.class, TEngineAppRuntime.class);
        }

        if(!glfwInit()){
            throw new InitializationException("Engine", "OpenGL GLFW init failed");
        }

        TwilightRuntimeService.init();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
    }
    public void setFPSLimit(int limit){
        frameLimit = limit;
    }


    public void stop(){
        runtime.forceStop();
    }

    private volatile boolean isError = false;
    private volatile Throwable error = null;

    @SneakyThrows
    private static @Nullable AbstractRuntime<TwilightApp> startRuntime(TwilightApp app){
        checkInit();
        AbstractRuntime<TwilightApp> runtime;
        runtime = TwilightRuntimeService.findRuntime(TwilightApp.class);
        if(runtime == null){
            throw new RuntimeException("No runtime found for TEngine app");
        }
        Thread thread = new Thread(() -> runtime.execute(app));
        thread.setName("TwLight Main Thread");

        thread.setUncaughtExceptionHandler((t, e) -> {
            logger.info(e);
            app.isError = true;
            app.error = e;
        });
        thread.start();

        long start = System.currentTimeMillis();

        while (app.getWindowPointer() == 0){
            if(app.isError){
                logger.fatal("Fatal error while starting the engine, there was an unexpected error during engine initialization.\n" +
                        "The logs above have more information", app.error);
                return null;
            }

            if((System.currentTimeMillis() - start) >= timeout){
                logger.fatal("Time for initialization has been exceeded, the engine did not start in the expected time.\nThe runtime abort run.");
                return null;
            }

            Thread.onSpinWait();
        }

        thread.setUncaughtExceptionHandler(null);


        app.keyboard = new TGLFWKeyboard(app);
        app.mouse = new TGLFWMouse(app);
        app.input = new InputProvider(app.keyboard, app.mouse);

        return runtime;
    }

    public static boolean isInit(){
        return isInit.get();
    }

    public static void startApp(TwilightApp app){
        AbstractRuntime<TwilightApp> runtime;
        runtime = startRuntime(app);
        if(runtime == null){
            throw new InitializationException("Engine was not initialized probably, look logs upper");
        }
        app.runtime = runtime;

    }

    private static void checkInit(){
        if(!isInit.get()){
            throw new InitializationException("TwLight", TwilightApp.class);
        }
    }

    private void checkStart(){
        if(!isInit()){
            throw new InitializationException("Before changing some settings you must first run app.");
        }
    }

    public void enableAutoWindowResizable(){
        glfwSetWindowSizeCallback(getWindowPointer(), new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int argWidth, int argHeight) {
               renderer.resizeWindow(argWidth, argHeight);

            }
        });
    }

    public void disableAutoWindowResizable(){
        glfwSetWindowSizeCallback(getWindowPointer(), null);
    }

    public void setWindowResizeCallback(DisplayResizeCallback callback){
        glfwSetWindowSizeCallback(getWindowPointer(), (window, width, height) -> callback.invoke(width, height));
    }

    public void disableWindowResizeCallback(){
        glfwSetWindowSizeCallback(getWindowPointer(), null);
    }


    public static void setRuntimeTimeout(long time){
        if(time <= 0 || time > 50000){
            throw new IllegalStateException("Timeout is must not higher than 50000 and lower than 0");
        }

        timeout = time;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void enginePulse();
}

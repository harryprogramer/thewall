package thewall.engine.tengine;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerContext;
import org.apache.logging.log4j.core.async.AsyncLoggerContextSelector;
import org.apache.logging.log4j.core.selector.ContextSelector;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import thewall.engine.tengine.audio.SoundMaster;
import thewall.engine.tengine.debugger.TEngineDebugger;
import thewall.engine.tengine.debugger.console.DebugConsole;
import thewall.engine.tengine.display.DisplayManager;
import thewall.engine.tengine.display.DisplayResizeCallback;
import thewall.engine.tengine.entity.Entity;
import thewall.engine.tengine.entity.Light;
import thewall.engine.tengine.errors.InitializationException;
import thewall.engine.tengine.input.InputProvider;
import thewall.engine.tengine.input.keyboard.Keyboard;
import thewall.engine.tengine.input.keyboard.TGLFWKeyboard;
import thewall.engine.tengine.input.keyboard.TKeyboardCallback;
import thewall.engine.tengine.input.keyboard.KeyboardKeys;
import thewall.engine.tengine.input.mouse.Mouse;
import thewall.engine.tengine.input.mouse.TGLFWMouse;
import thewall.engine.tengine.models.Loader;
import thewall.engine.tengine.render.MasterRenderer;
import thewall.engine.tengine.runtime.AbstractRuntime;
import thewall.engine.tengine.runtime.app.TEngineAppRuntime;
import thewall.engine.tengine.runtime.TEngineRuntime;
import thewall.engine.tengine.terrain.Terrain;

import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public abstract class TEngineApp {
    @Getter
    public static final String version = "1.0.4";

    @Getter
    @Setter
    private DebugConsole debugConsole = DebugConsole.getConsole();

    private final static Logger logger = LogManager.getLogger(TEngineApp.class);

    private volatile AbstractRuntime<TEngineApp> runtime;
    private volatile boolean isStackTraceEnabled = false;
    private static final AtomicBoolean isInit = new AtomicBoolean(false);
    private PrintWriter logCallback;
    private Thread renderThread;
    private static double previousTime = glfwGetTime();
    @Getter
    private volatile int frameLimit = 60; // DEFAULT FRAME LIMIT

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
    private volatile DisplayManager displayManager;

    @Getter
    @Setter
    private volatile SoundMaster soundMaster;

    @Getter
    private int windowWidth = 1280, windowHeight = 720;

    @Getter
    @Setter
    private volatile MasterRenderer renderer;

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
        glfwSetWindowTitle(displayManager.getWindow(), windowTitle);
    }

    public void enableDebugConsole(){

    }

    public void enableVSync(){
        checkInit();
        displayManager.enableVSync();
    }

    public void disableVSync(){
        checkInit();
        displayManager.disableVSync();
    }

    public void setLogCallback(PrintWriter callback){
        logCallback = callback;
    }

    public void setKeyboardCallback(TKeyboardCallback TKeyboardCallback){
        checkInit();
        runtime.executeTask(() -> glfwSetKeyCallback(displayManager.getWindow(), (window, key, scancode, action, mods) -> {
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

        TEngineDebugger.setPrintProxyDebugger(DebugConsole.getConsole());

        if(isInit.compareAndSet(false, true)) {
            TEngineRuntime.registerRuntime(TEngineApp.class, TEngineAppRuntime.class);
        }


        if(!glfwInit()){
            throw new InitializationException("Engine", "OpenGL GLFW init failed");
        }
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
    private static AbstractRuntime<TEngineApp> startRuntime(TEngineApp app){
        checkInit();
        AbstractRuntime<TEngineApp> runtime;
        runtime = TEngineRuntime.findRuntime(TEngineApp.class);
        if(runtime == null){
            throw new RuntimeException("No runtime found for TEngine app");
        }
        Thread thread = new Thread(() -> runtime.execute(app));
        thread.setName("TEngine Main Thread");
        thread.start();

        thread.setUncaughtExceptionHandler((t, e) -> {
           app.isError = true;
           app.error = e;
        });

        while (!(!(app.displayManager == null) && !((app.displayManager != null ? app.displayManager.getWindow() : 0) == 0))){
            if(app.isError){
                logger.fatal("Fatal error when trying run [" + app.getName() + "], TEngine Runtime aborting start", app.error);
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

    public static void startApp(TEngineApp app){
        AbstractRuntime<TEngineApp> runtime;
        runtime = startRuntime(app);
        if(runtime == null){
            throw new InitializationException("Engine was not initialized probably, look logs upper");
        }
        app.runtime = runtime;

        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));
    }

    private static void checkInit(){
        if(!isInit.get()){
            throw new InitializationException("TEngine", TEngineApp.class);
        }
    }

    private void checkStart(){
        if(displayManager == null){
            throw new InitializationException("Before changing some settings you must first run app.");
        }
    }

    public void enableAutoWindowResizable(){
        glfwSetWindowSizeCallback(displayManager.getWindow(), new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int argWidth, int argHeight) {
               renderer.resizeWindow(argWidth, argHeight);
            }
        });
    }

    public void disableAutoWindowResizable(){
        glfwSetWindowSizeCallback(displayManager.getWindow(), null);
    }

    public void setWindowResizeCallback(DisplayResizeCallback callback){
        glfwSetWindowSizeCallback(displayManager.getWindow(), (window, width, height) -> callback.invoke(width, height));
    }

    public void disableWindowResizeCallback(){
        glfwSetWindowSizeCallback(displayManager.getWindow(), null);
    }


    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void enginePulse();
}

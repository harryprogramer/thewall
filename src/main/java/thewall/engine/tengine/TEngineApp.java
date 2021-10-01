package thewall.engine.tengine;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import thewall.engine.tengine.audio.SoundMaster;
import thewall.engine.tengine.debugger.console.DebugConsole;
import thewall.engine.tengine.display.DisplayManager;
import thewall.engine.tengine.display.DisplayResizeCallback;
import thewall.engine.tengine.entity.Entity;
import thewall.engine.tengine.entity.Light;
import thewall.engine.tengine.errors.InitializationException;
import thewall.engine.tengine.input.keyboard.TKeyboardCallback;
import thewall.engine.tengine.input.keyboard.KeyboardKeys;
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
    private String name;

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
                    TKeyboardCallback.invoke(KeyboardKeys.keyToEnum(key), scancode, action, mods);
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
        logger.info("Initializing engine...");

        class Dummy {
            public void m() {
            }
        }
        for (int i = 0; i < 10000000; i++) {
            Dummy dummy = new Dummy();
            dummy.m();
        }

        if(isInit.compareAndSet(false, true)) {
            TEngineRuntime.registerRuntime(TEngineApp.class, TEngineAppRuntime.class);
        }

        if(!glfwInit()){
            throw new InitializationException("Engine", "OpenGL GLFW init failed");
        }
        glfwDefaultWindowHints();
        //glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_FALSE);
    }
    public void setFPSLimit(int limit){
        frameLimit = limit;
    }


    public void stop(){
        runtime.forceStop();
        System.exit(1);
    }

    private static @NotNull AbstractRuntime<TEngineApp> startRuntime(TEngineApp app){
        checkInit();
        AbstractRuntime<TEngineApp> runtime;
        runtime = TEngineRuntime.findRuntime(TEngineApp.class);
        if(runtime == null){
            throw new RuntimeException("No runtime found for TEngine app");
        }
        Thread thread = new Thread(() -> runtime.execute(app));
        thread.setName("TEngine Main Thread");
        thread.start();

        while (app.displayManager == null || app.displayManager.getWindow() == 0){
            Thread.onSpinWait();
        }
        return runtime;
    }

    public static void startApp(TEngineApp app){
        AbstractRuntime<TEngineApp> runtime;
        runtime = startRuntime(app);
        app.runtime = runtime;
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

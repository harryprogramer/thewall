package thewall.engine.twilight.runtime.app;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import thewall.engine.twilight.TwilightApp;
import thewall.engine.twilight.display.GLFWDisplayManager;
import thewall.engine.twilight.input.gamepad.GLFWJoystickCallback;
import thewall.engine.twilight.render.MasterRenderer;
import thewall.engine.twilight.render.SyncTimer;
import thewall.engine.twilight.runtime.AbstractRuntime;

import java.util.*;
import java.util.concurrent.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class TEngineAppRuntime extends AbstractRuntime<TwilightApp> {
    private int errorRepeatCount = 0;
    private Exception lastError = null;
    private final static Logger logger = LogManager.getLogger(TEngineAppRuntime.class);
    private final SyncTimer syncTimer = new SyncTimer(SyncTimer.LWJGL_GLFW);
    private final static ExecutorService scheduler = Executors.newSingleThreadExecutor();
    private TwilightApp twilightApp;
    volatile boolean isInit = false;
    private Thread runtimeThread = null;


    private long windowPointer;

    volatile boolean isClosing = false;
    volatile boolean isClosed = false;

    private final List<Runnable> rendererTasks = new ArrayList<>();


    public TEngineAppRuntime() {
        super("Twilight Game App Runtime");
    }

    @Override
    protected Thread getThread() {
        return runtimeThread;
    }

    @Override
    public void executeTask(Runnable runnable) {
        rendererTasks.add(runnable);
    }

    @Override
    protected void start(@NotNull TwilightApp program) {
        try {
            runtimeThread = Thread.currentThread();
            if (!program.getDebugConsole().isLogging())
                program.getDebugConsole().startLogging();
            program.createDisplay();
            glfwFocusWindow(program.getWindowPointer());
            GL.createCapabilities();
            program.setRenderer(new MasterRenderer(program));
            try {
                program.onEnable();
            }catch (Exception e){
                logger.fatal("Exception in app initialization function", e);
                forceStop();
            }
            logger.info("OpenGL:                " + GL11.glGetString(GL11.GL_VERSION));
            logger.info("GPU:                   " + GL11.glGetString(GL11.GL_RENDERER));
            logger.info("OpenGL Vendor:         " + GL11.glGetString(GL11.GL_VENDOR));
            this.windowPointer = program.getWindowPointer();
            this.twilightApp = program;
            glfwSetJoystickCallback(new GLFWJoystickCallback(this.twilightApp));
            engineLoop();
        }catch (Exception e){
            logger.fatal("An unknown error has occurred while trying to start the engine core", e);
            forceStop();
        }
    }

    private List<Integer> getActiveControllers(){
        int[] controllerDictionary = {GLFW_JOYSTICK_1, GLFW_JOYSTICK_2, GLFW_JOYSTICK_3,
                GLFW_JOYSTICK_4, GLFW_JOYSTICK_5, GLFW_JOYSTICK_6, GLFW_JOYSTICK_7,
                GLFW_JOYSTICK_8, GLFW_JOYSTICK_9, GLFW_JOYSTICK_10, GLFW_JOYSTICK_11,
                GLFW_JOYSTICK_12, GLFW_JOYSTICK_13, GLFW_JOYSTICK_14, GLFW_JOYSTICK_15,
                GLFW_JOYSTICK_16};

        List<Integer> controllers = new ArrayList<>();

        for(int i = 0; i < 15; i++){
            if(glfwJoystickPresent(controllerDictionary[i])){
                controllers.add(i + 1);
            }
        }

        return controllers;
    }

    @Override
    protected synchronized void stop() {
        if(!isClosed) {
            isClosing = true;
            logger.info("Shutting down [" + twilightApp.getName() + "] ...");
            stopEngine();
            twilightApp.onDisable();
            twilightApp.getDebugConsole().stopLogging();
            twilightApp.getDebugConsole().closeConsole();
            logger.info("Closing app...");
            isClosed = true;
            System.exit(-1);
        }
    }

    private void stopEngine(){
        twilightApp.getRenderer().cleanUp();
        twilightApp.getLoader().cleanUp();

        glfwFreeCallbacks(twilightApp.getWindowPointer());
        glfwDestroyWindow(twilightApp.getWindowPointer());
        glfwTerminate();
        try {
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }catch (Exception ignored){

        }
        scheduler.shutdown();
    }

    @SneakyThrows
    private void engineLoop(){

        while (!glfwWindowShouldClose(windowPointer)) {
            try {
                if(isClosing){
                    break;
                }
                if (!rendererTasks.isEmpty()) {
                    for (Iterator<Runnable> it = rendererTasks.iterator(); it.hasNext(); ) {
                        Runnable task = it.next();
                        try {
                            task.run();
                        } catch (Exception e) {
                            logger.warn("Render task error", e);
                        }
                        it.remove();
                    }
                }
                twilightApp.updateDisplay();
                twilightApp.enginePulse();
            }catch (Exception e){
                if(lastError != null){
                    if(e.getClass() == lastError.getClass()){
                        if(++errorRepeatCount % 100 == 0){
                            logger.warn(e.getClass().getSimpleName() + " is still throwing in the engine loop, more then " + errorRepeatCount + " times");
                        }
                        if(errorRepeatCount >= 500){
                            logger.fatal("Too much error for this same exception, shutting down...");
                            logger.fatal("Error stacktrace", e);
                            forceStop();
                        }
                    }
                }else {
                    logger.warn("Error while pulsing engine", e);
                }
                lastError = e;

            }
            syncTimer.sync(twilightApp.getFrameLimit());
        }

        stop();
    }


    private void scheduleTask(Runnable r){
        rendererTasks.add(r);
    }

}

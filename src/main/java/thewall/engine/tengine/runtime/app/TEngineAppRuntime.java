package thewall.engine.tengine.runtime.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFWErrorCallback;
import thewall.engine.tengine.TEngineApp;
import thewall.engine.tengine.debugger.TEngineDebugger;
import thewall.engine.tengine.display.DisplayManager;
import thewall.engine.tengine.render.MasterRenderer;
import thewall.engine.tengine.render.SyncTimer;
import thewall.engine.tengine.runtime.AbstractRuntime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class TEngineAppRuntime extends AbstractRuntime<TEngineApp> {
    private int errorRepeatCount = 0;
    private Exception lastError = null;
    private final static Logger logger = LogManager.getLogger(TEngineAppRuntime.class);
    private final SyncTimer syncTimer = new SyncTimer(SyncTimer.LWJGL_GLFW);
    private final static ExecutorService scheduler = Executors.newSingleThreadExecutor();
    private TEngineApp tEngineApp;
    volatile boolean isInit = false;
    private Thread runtimeThread = null;

    volatile boolean isClosing = false;
    volatile boolean isClosed = false;

    private final List<Runnable> rendererTasks = new ArrayList<>();


    public TEngineAppRuntime() {
        super("TEngine Game App Runtime");
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
    protected void start(@NotNull TEngineApp program) {
        runtimeThread = Thread.currentThread();
        TEngineDebugger.setPrintProxyDebugger(program.getDebugConsole());
        if(!program.getDebugConsole().isLogging())
            program.getDebugConsole().startLogging();
        program.setDisplayManager(new DisplayManager(program.getWindowWidth(), program.getWindowHeight(), program));
        /*
        scheduler.execute(() -> {

        });

         */


        program.getDisplayManager().createDisplay();
        glfwFocusWindow(program.getDisplayManager().getWindow());
        program.setRenderer(new MasterRenderer(program.getDisplayManager()));
        program.onEnable();
        this.tEngineApp = program;

        engineLoop();
    }

    @Override
    protected synchronized void stop() {
        if(!isClosed) {
            isClosing = true;
            logger.info("Shutting down [" + tEngineApp.getName() + "] ...");
            stopEngine();
            tEngineApp.onDisable();
            tEngineApp.getDebugConsole().stopLogging();
            tEngineApp.getDebugConsole().closeConsole();
            logger.info("Closing app...");
            isClosed = true;
            System.exit(-1);
        }
    }

    private void stopEngine(){
        tEngineApp.getRenderer().cleanUp();
        tEngineApp.getLoader().cleanUp();

        glfwFreeCallbacks(tEngineApp.getDisplayManager().getWindow());
        glfwDestroyWindow(tEngineApp.getDisplayManager().getWindow());
        glfwTerminate();
        try {
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }catch (Exception ignored){

        }
        scheduler.shutdown();
    }

    private void engineLoop(){
        while (!glfwWindowShouldClose(tEngineApp.getDisplayManager().getWindow())) {
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

                    tEngineApp.getDisplayManager().updateDisplay();
                    tEngineApp.enginePulse();
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
            try {
                syncTimer.sync(tEngineApp.getFrameLimit());
            } catch (Exception ex) {
                logger.warn("Error syncing fps limit", ex);
            }
        }

        stop();
    }


    private void scheduleTask(Runnable r){
        rendererTasks.add(r);
    }
}

package thewall.game.engine;

import lombok.Getter;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.util.vector.Vector3f;
import thewall.game.engine.audio.SoundManager;
import thewall.game.engine.audio.SoundMaster;
import thewall.game.engine.display.DisplayManager;
import thewall.game.engine.entity.Camera;
import thewall.game.engine.entity.Entity;
import thewall.game.engine.entity.Light;
import thewall.game.engine.input.KeyboardCallback;
import thewall.game.engine.input.KeyboardKeys;
import thewall.game.engine.models.Loader;
import thewall.game.engine.render.MasterRenderer;
import thewall.game.engine.terrain.Terrain;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public abstract class PogEngineApp {
    private PrintWriter logCallback;
    private Thread renderThread;
    private static double previousTime = glfwGetTime();
    private int frameCount;

    @Getter
    private Light rndrLight = new Light(new Vector3f(0, 0, 0) ,new Vector3f(0, 0, 0));
    @Getter
    private Camera rndrCamera = new Camera();

    @Getter
    private final Loader loader = new Loader();

    @Getter
    private DisplayManager displayManager;

    @Getter
    private SoundMaster soundMaster;

    @Getter
    private int windowWidth = 1280, windowHeight = 720;

    private MasterRenderer renderer;

    /**
     * Initialize engine and create display
     * */
    public void startEngine() throws InterruptedException {
        onEnable();
        initializeEngine();
        startEnginePulse();
        TimeUnit.SECONDS.sleep(1);
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
        glfwSetWindowTitle(displayManager.getWindow(), windowTitle);
    }

    private void initializeEngine(){
        if (!glfwInit()) {
            System.err.println("OpenGL init error");
        }
        displayManager = new DisplayManager(windowWidth, windowHeight);
        soundMaster = new SoundManager();
    }

    /**
     * Pulse and update the display
     * */
    public void pulseRender() throws Exception {
        updateDisplay();
    }

    public void enableVSync(){
        displayManager.enableVSync();
    }

    public void disableVSync(){
        displayManager.disableVSync();
    }

    public int getFPS(){
        return frameCount;
    }

    public void stopEngine(){
        onDisable();
        cleanUpWindow();
    }

    private void cleanUpWindow(){
        glfwFreeCallbacks(displayManager.getWindow());
        glfwDestroyWindow(displayManager.getWindow());

        glfwTerminate();
        try {
            Objects.requireNonNull(glfwSetErrorCallback(null)).free();
        }catch (NullPointerException ignored){

        }
    }

    public void setLogCallback(PrintWriter callback){
        logCallback = callback;
    }

    public void setKeyboardCallback(KeyboardCallback keyboardCallback){
        glfwSetKeyCallback(displayManager.getWindow(), (window, key, scancode, action, mods) -> keyboardCallback.invoke(keyToEnum(key), scancode, action, mods));
    }

    private void updateDisplay() throws Exception {
        displayManager.updateDisplay();

        double currentTime = glfwGetTime();
        frameCount++;
        if (currentTime - previousTime >= 1.0) {
            previousTime = currentTime;
            frameCount = 0;
        }
    }

    @Blocking
    private void startEngineLoop() throws Exception {
        displayManager.createDisplay();
        renderer = new MasterRenderer(displayManager);

        while(!glfwWindowShouldClose(displayManager.getWindow())){
            enginePulse();
            updateDisplay();
            renderer.render(rndrLight, rndrCamera);
        }
    }

    private void startEnginePulse(){
         renderThread = new Thread(() -> {
             try {
                 startEngineLoop();
             } catch (Exception e) {
                 e.printStackTrace();
                 stopEngine();
             }
         });
         renderThread.start();
    }

    public void rndrProcessTerrain(Terrain terrain){
        renderer.processTerrain(terrain);
    }

    public void rndrProcessEntity(Entity entity){
        renderer.processEntity(entity);
    }

    public void rndrProcessLight(Light light){
        this.rndrLight = light;
    }

    public void rndrProcessCamera(Camera camera){
        this.rndrCamera = camera;
    }

    @Contract(pure = true)
    private @Nullable KeyboardKeys keyToEnum(int code){
        switch (code){
            case GLFW_KEY_W -> {
                return KeyboardKeys.W_KEY;
            }

            case GLFW_KEY_D -> {
                return KeyboardKeys.D_KEY;
            }

            default -> {return null;}
        }
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void enginePulse();
}

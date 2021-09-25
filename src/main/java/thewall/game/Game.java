package thewall.game;

import thewall.game.engine.audio.SoundChannel;
import thewall.game.engine.debugger.TEngineDebugger;
import thewall.game.engine.debugger.console.DebugConsole;
import thewall.game.engine.display.DisplayManager;
import thewall.game.engine.entity.Camera;
import thewall.game.engine.entity.Entity;
import thewall.game.engine.entity.Light;
import thewall.game.engine.models.Loader;
import thewall.game.engine.models.RawModel;
import thewall.game.engine.models.TexturedModel;
import thewall.game.engine.models.obj.thinmatrix.ModelData;
import thewall.game.engine.models.obj.thinmatrix.OBJFileLoader;
import thewall.game.engine.models.obj.thinmatrix.OBJLoader;
import thewall.game.engine.render.MasterRenderer;
import thewall.game.engine.render.SyncTimer;
import thewall.game.engine.audio.SoundManager;
import thewall.game.engine.terrain.Terrain;
import thewall.game.engine.textures.ModelTexture;
import lombok.Getter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class Game {
    @Getter
    private static final DisplayManager displayManager = new DisplayManager(1280, 720);
    private final static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    @Getter
    private static final DebugConsole debug = new DebugConsole();

    static double previousTime = glfwGetTime();
    static int frameCount = 0;
    static volatile int fps;

    static SyncTimer timer  = new SyncTimer(SyncTimer.LWJGL_GLFW);

    private static final SoundManager soundManager = new SoundManager();

    @Getter
    private static MasterRenderer masterRenderer;

    public float random() {
        Random r = new Random();
        return .0f + r.nextFloat() * (.9f - .0f);
    }

    public static void main(String[] args) throws Exception {
        TEngineDebugger.setPrintProxyDebugger(debug);
        debug.startLogging();
        System.out.println("Loading game...");
        class Dummy {
            public void m() {
            }
        }

        for (int i = 0; i < 100000; i++) { // warm up jvm
            Dummy dummy = new Dummy();
            dummy.m();
        }

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        long renderPrepareTime, shaderTime, rendererTime, updateDisplayTime, shaderStopTime;

        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println(glfwGetVersionString());
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            System.err.println("open gl error");
        }

        debug.showConsole();



        displayManager.createDisplay();
        masterRenderer = new MasterRenderer(displayManager);
        Loader loader = new Loader();
        //OBJObject objObject = new OBJObject(new File("./res/models/cube"));

        //RawModel treeRawModel = loader.loadToVAO(ExampleModels.krzysztof, ExampleModels.krzysztofIndices, ExampleModels.krzysztofTextureCoords);

        ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
        ModelData grassModelData = OBJFileLoader.loadOBJ("grassModel");
        ModelData lowPolyTree = OBJFileLoader.loadOBJ("lowPolyTree");

        RawModel treeRawModel = loader.loadToVAO(treeModelData.getVertices(), treeModelData.getIndices(),
                treeModelData.getTextureCoords(), treeModelData.getNormals());

        TexturedModel treemodel = new TexturedModel(treeRawModel, new ModelTexture(loader.loadTexture("tree", GL_RGBA)));
        TexturedModel grassModel = new TexturedModel(loader.loadToVAO(grassModelData.getVertices(), grassModelData.getIndices(),
                grassModelData.getTextureCoords(), grassModelData.getNormals()), new ModelTexture(loader.loadTexture("grassTexture", GL_RGBA)));

        TexturedModel lowPolyTreeModel = new TexturedModel(loader.loadToVAO(lowPolyTree.getVertices(), lowPolyTree.getIndices(),
                lowPolyTree.getTextureCoords(), lowPolyTree.getNormals()),  new ModelTexture(loader.loadTexture("lowPolyTree", GL_RGBA)));

        ModelTexture texture = treemodel.getModelTexture();

        texture.setShineDamper(10);
        texture.setReflectivity(1);


        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));


        //SoundChannel soundChannel = soundManager.playBackground(.3f, 0, "output.wav");
        Camera camera = new Camera();

        List<Entity> worldEntities = new ArrayList<>();

        Terrain terrain = new Terrain(0, 0, loader, new ModelTexture(loader.loadTexture("grass3", GL_RGBA)));
        Terrain terrain2 = new Terrain(1, 0, loader, new ModelTexture(loader.loadTexture("grass3", GL_RGBA)));

        terrain.getTexture().setUseFakeLighting(true);
        terrain2.getTexture().setUseFakeLighting(true);

        /*
        // drzewa default
        for(int i = 0; i < 100; i++){
            int x = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            int z = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            float size = 1.2f + new Random().nextFloat() * (2.0f - 1.2f);
            worldEntities.add(new Entity(treemodel, new Vector3f(x, 0, z), 0, 180, 0, size));
        }

         */


        // trawa
        for(int i = 0; i < 600; i++){
            int x = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            int z = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            worldEntities.add(new Entity(grassModel, new Vector3f(x, 0, z), 0, 180, 0, 3));
        }

        // drzewa low poly
        for(int i = 0; i < 400; i++){
            int x = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            int z = ThreadLocalRandom.current().nextInt(100, 300 + 1);
            float size = 0.2f + new Random().nextFloat() * (0.3f - 0.2f);
            worldEntities.add(new Entity(lowPolyTreeModel, new Vector3f(x, 0, z), 0, 180, 0, 1));
        }


        glfwSetInputMode(displayManager.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        while (!glfwWindowShouldClose(displayManager.getWindow())) {
            //debug.info("test");
            try {

                //entity.increasePosition(0, 0, -0.006f);
                //entity.increaseRotation(0, 0.1f, 0);
                camera.move();

                displayManager.updateDisplay();

                masterRenderer.processTerrain(terrain);
                //
                masterRenderer.processTerrain(terrain2);

                for(Entity ent : worldEntities){
                    masterRenderer.processEntity(ent);
                }
                masterRenderer.render(light, camera);

                double currentTime = glfwGetTime();
                frameCount++;
                if (currentTime - previousTime >= 1.0) {
                    glfwSetWindowTitle(displayManager.getWindow(), "FPS: " + frameCount);
                    fps = frameCount;
                    System.out.flush();
                    debug.debug("FPS: " + frameCount);
                    System.out.printf("X: [%s] Y: [%s] Z: [%s]\n", camera.getPosition().x, camera.getPosition().y, camera.getPosition().z);
                    //System.out.println("Root: " + String.valueOf(System.nanoTime() - tickStartTime / 1000000.0).substring(0, 4) + "ms");
                    frameCount = 0;
                    previousTime = currentTime;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            //timer.sync(100);
        }

        debug.closeConsole();
        masterRenderer.cleanUp();
        loader.cleanUp();

        glfwFreeCallbacks(displayManager.getWindow());
        glfwDestroyWindow(displayManager.getWindow());

        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();


    }

    private static class ShutdownHook extends Thread {
        @Override
        public void run() {
            Logger.getLogger(Game.class.getSimpleName()).log(Level.INFO, "Shutting down...");
        }
    }
}

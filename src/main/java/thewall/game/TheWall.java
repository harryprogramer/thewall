package thewall.game;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import thewall.engine.twilight.TwilightApp;
import thewall.engine.twilight.entity.Entity;
import thewall.engine.twilight.entity.Light;
import thewall.engine.twilight.entity.Player;
import thewall.engine.twilight.entity.RawEntity;
import thewall.engine.twilight.models.RawModel;
import thewall.engine.twilight.models.TexturedModel;
import thewall.engine.twilight.models.obj.thinmatrix.ModelData;
import thewall.engine.twilight.models.obj.thinmatrix.OBJFileLoader;
import thewall.engine.twilight.terrain.Terrain;
import thewall.engine.twilight.textures.ModelTexture;
import thewall.engine.twilight.textures.TerrainTexture;
import thewall.engine.twilight.textures.TerrainTexturePack;
import thewall.game.events.GamepadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TheWall extends TwilightApp {
    static int frameCount = 0;
    static double previousTime = glfwGetTime();

    private static final long  MEGABYTE = 1024L * 1024L;

    private final static Logger logger = LogManager.getLogger(TheWall.class);

    private final ModelData treeModelData = OBJFileLoader.loadOBJ("tree");
    private final ModelData grassModelData = OBJFileLoader.loadOBJ("fern");
    private final ModelData lowPolyTree = OBJFileLoader.loadOBJ("lowPolyTree");

    private RawModel treeRawModel;

    private TexturedModel treemodel;
    private TexturedModel grassModel;

    private TexturedModel lowPolyTreeModel;

    private ModelTexture texture;

    private ModelData modelData = OBJFileLoader.loadOBJ("bunny");
    private RawModel bunnyModel;
    private Player player;

    private Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

    private TerrainTexture backgroundTexture;
    private TerrainTexture rTexture ;
    private TerrainTexture gTexture ;
    private TerrainTexture bTexture ;

    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;

    private Terrain terrain;
    private Terrain terrain2;

    private List<Entity> worldEntities = new ArrayList<>();

    public TheWall() {
        setName("The Wall");
    }

    @Override
    public void onEnable() {
        bunnyModel = getLoader().loadToVAO(modelData.getVertices(), modelData.getIndices(), modelData.getTextureCoords(), modelData.getNormals());
        grassModel = new TexturedModel(getLoader().loadToVAO(grassModelData.getVertices(), grassModelData.getIndices(),
                grassModelData.getTextureCoords(), grassModelData.getNormals()), new ModelTexture(getLoader().loadTexture("fern", GL_RGBA, GL_NEAREST)));
        grassModel.getModelTexture().setNumberOfRows(2);
        treeRawModel = getLoader().loadToVAO(treeModelData.getVertices(), treeModelData.getIndices(),
                treeModelData.getTextureCoords(), treeModelData.getNormals());
        lowPolyTreeModel = new TexturedModel(getLoader().loadToVAO(lowPolyTree.getVertices(), lowPolyTree.getIndices(),
                lowPolyTree.getTextureCoords(), lowPolyTree.getNormals()),  new ModelTexture(getLoader().loadTexture("lowPolyTree", GL_RGBA,GL_NEAREST )));

        backgroundTexture = new TerrainTexture(getLoader().loadTexture("grass3", GL_RGBA, GL_LINEAR));
        rTexture = new TerrainTexture(getLoader().loadTexture("mud", GL_RGBA, GL_LINEAR));
        gTexture = new TerrainTexture(getLoader().loadTexture("grassFlowers", GL_RGBA, GL_LINEAR));
        bTexture = new TerrainTexture(getLoader().loadTexture("path", GL_RGBA, GL_NEAREST));
        blendMap = new TerrainTexture(getLoader().loadTexture("blendMap", GL_RGBA, GL_NEAREST));


        texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        terrain = new Terrain(0, 0, getLoader(), texturePack, blendMap, "heightMap");
        treemodel = new TexturedModel(treeRawModel, new ModelTexture(getLoader().loadTexture("tree", GL_RGBA, GL_NEAREST)));

        player = new Player(new TexturedModel(bunnyModel,new ModelTexture(getLoader().loadTexture("white", GL_RGBA, GL_LINEAR))),
                new Vector3f(300, 0, 600), 0, 0, 0, 1, terrain);
        texture = treemodel.getModelTexture();

        player.hide();

        enableAutoWindowResizable();

        texture.setShineDamper(10);
        texture.setReflectivity(1);

        for(int i = 0; i < 400; i++){
            int x = ThreadLocalRandom.current().nextInt(0, 800 + 1);
            int z = ThreadLocalRandom.current().nextInt(0, 800 + 1);
            worldEntities.add(new RawEntity(grassModel, new Random().nextInt(4) ,new Vector3f(x, terrain.getHeightOfTerrain(x, z), z), 2, terrain));
        }

        // drzewa low poly
        for(int i = 0; i < 400; i++){
            int x = ThreadLocalRandom.current().nextInt(0, 800 + 1);
            int z = ThreadLocalRandom.current().nextInt(0, 800 + 1);
            float size = 0.2f + new Random().nextFloat() * (0.3f - 0.2f);
            worldEntities.add(new RawEntity(lowPolyTreeModel, new Vector3f(x, terrain.getHeightOfTerrain(x, z), z), 1, terrain));
        }
        //ModelData wtcModelData = OBJFileLoader.loadOBJ("");
        //TexturedModel wtcModel = new TexturedModel(getLoader().loadToVAO(wtcModelData.getVertices(), wtcModelData.getIndices(),
        //        wtcModelData.getTextureCoords(), wtcModelData.getNormals()), new ModelTexture(getLoader().loadTexture("", GL_RGBA, GL_NEAREST)));
       // worldEntities.add(new Entity(wtcModel, new Vector3f(250, 0, 250), 0, 0, 0,1));

        getEventManager().registerEvents(new GamepadEvent());

        player.enableCollisionSystem();

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void enginePulse() {

        player.tick();

        getRenderer().processEntity(player);

        getRenderer().processTerrain(terrain);

        for (Entity ent : worldEntities) {
            getRenderer().processEntity(ent);
        }

        getRenderer().render(light, player.getCamera());


        double currentTime = glfwGetTime();
        frameCount++;
        if (currentTime - previousTime >= 1.0) {
            long total = getRealtimeHardware().getMemory().getTotalAllocated() / MEGABYTE;
            long available = getRealtimeHardware().getMemory().getAvailableAllocated() / MEGABYTE;
            setWindowTitle("MEMORY: " + available + "MB "
                    + "/ " + total + "MB"
                    + " | FPS: " + frameCount + " | " + getRealtimeHardware().getProcessor().getName() + " | " + getRealtimeHardware().getUsedGraphic().getName());
            if (frameCount <= 30) {
                logger.warn("FPS drop detected, current framerate: " + frameCount);
            }
            frameCount = 0;
            previousTime = currentTime;
        }

    }

    private static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    private static long getUsedMemory() {
        return getMaxMemory() - getFreeMemory();
    }

    private static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    private static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

}

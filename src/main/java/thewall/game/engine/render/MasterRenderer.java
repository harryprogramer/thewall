package thewall.game.engine.render;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import thewall.game.engine.display.DisplayManager;
import thewall.game.engine.display.DisplayUtils;
import thewall.game.engine.display.Resolution;
import thewall.game.engine.entity.Camera;
import thewall.game.engine.entity.Entity;
import thewall.game.engine.entity.Light;
import thewall.game.engine.models.TexturedModel;
import thewall.game.engine.shaders.StaticShader;
import thewall.game.engine.shaders.TerrainShader;
import thewall.game.engine.terrain.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {
    private final static float FOV = 80;
    private final static float NEAR_PLANE = 0.1f;
    private final static float FAR_PLANE = 1200;

    private static final float RED = 0.6f;
    private static final float GREEN = 0.78f;
    private static final float BLUE = 0.76f;

    private Matrix4f projectionMatrix;
    private final DisplayManager displayManager;
    private final TerrainRenderer terrainRenderer;
    private final TerrainShader terrainShader = new TerrainShader();

    private final List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer(DisplayManager displayManager){
        this.displayManager = displayManager;
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL_BACK);
        createProjectionMatrix(-1, -1);
        entityRenderer = new EntityRenderer(displayManager, shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }

    private final StaticShader shader = new StaticShader();
    @Getter
    private final EntityRenderer entityRenderer;

    private final Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void processEntity(@NotNull Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void render(Light sun, Camera camera){
        prepare();
        shader.start();
        shader.loadSkyColor(RED, GREEN, BLUE);
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColor(RED, GREEN, BLUE);
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
    }

    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
    }

    private void createProjectionMatrix(int width, int height){
        Resolution resolution;

        if(width != -1 && height != -1){
            resolution = new Resolution(width, height);
        }else {
            resolution = DisplayUtils.getWindowSize(displayManager.getWindow());

        }
        float aspectRatio = (float) resolution.getWidth() / (float) resolution.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(RED, GREEN, BLUE, 1);
    }

    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }

    public void resizeWindow(int argWidth, int argHeight) {
        glViewport(0, 0, argWidth,argHeight);
        createProjectionMatrix(argWidth, argHeight);
        //adjustProjectionMatrix(width, height); // recalculating projection matrix (only if you are using one)
    }

    public static void enableCulling(){
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL_BACK);
    }

    public static void disableCulling(){
        GL11.glDisable(GL_CULL_FACE);
    }
}

package thewall.engine.twilight.renderer.opengl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector2i;
import thewall.engine.twilight.Node;
import thewall.engine.twilight.RenderQueue;
import thewall.engine.twilight.ViewPort;
import thewall.engine.twilight.display.Display;
import thewall.engine.twilight.entity.Camera;
import thewall.engine.twilight.entity.Light;
import thewall.engine.twilight.entity.Spatial;
import thewall.engine.twilight.errors.TextureDecoderException;
import thewall.engine.twilight.material.Material;
import thewall.engine.twilight.math.Maths;
import thewall.engine.twilight.models.TexturedModel;
import thewall.engine.twilight.renderer.Renderer;
import thewall.engine.twilight.renderer.TerrainRenderer;
import thewall.engine.twilight.renderer.opengl.vao.VAOManager;
import thewall.engine.twilight.shaders.StaticShader;
import thewall.engine.twilight.shaders.TerrainShader;
import thewall.engine.twilight.skybox.SkyboxRender;
import thewall.engine.twilight.terrain.Terrain;
import thewall.engine.twilight.texture.PixelFormat;
import thewall.engine.twilight.texture.TerrainTexture;
import thewall.engine.twilight.texture.TerrainTexturePack;
import thewall.engine.twilight.texture.opengl.GLTextureManager;
import thewall.engine.twilight.utils.Colour;
import thewall.engine.twilight.utils.Validation;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static thewall.engine.twilight.renderer.opengl.GL3.*;

/**
 * bede sie z tym meczyc do nowego roku
 * wiec wstawiam choinke bo lepiej wczesniej niz pozniej
 * <p></p>
 * <img width="2000" src="https://www.history.com/.image/ar_1:1%2Cc_fill%2Ccs_srgb%2Cfl_progressive%2Cq_auto:good%2Cw_1200/MTY4OTA4MzI0ODc4NjkwMDAw/christmas-tree-gettyimages-1072744106.jpg" />
 */
public class GLRenderer implements Renderer {
    private Colour backgroundColour = Colour.BLACK;
    private final static Logger logger = LogManager.getLogger(GLRenderer.class);
    //private RenderQueue currentQueue = new RenderQueue();
    private Map<Material, List<Spatial>> queue = new HashMap<>();
    private Matrix4f viewMatrix;

    private SkyboxRender skyboxRender;

    private StaticShader shader;

    private final GL gl;
    private final GL gl2;
    private final GL gl3;
    private final Display display;
    private final VAOManager vaoManager;
    private final GLTextureManager textureManager;

    private final List<Class<?>> deprecatedList = new ArrayList<>();

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader;

    private final List<Terrain> terrains = new ArrayList<>();

    public GLRenderer(GL gl, VAOManager vao, GLTextureManager glTextureManager, Display display){
        Validation.checkNull(gl);
        Validation.checkNull(vao);
        Validation.checkNull(glTextureManager);
        this.gl = gl;
        this.gl2 = gl instanceof GL2 ? (GL2) gl : null;
        this.gl3 = gl instanceof GL3 ? (GL3) gl : null;
        this.vaoManager = vao;
        this.textureManager = glTextureManager;
        this.display = display;

        String error = "";

        if(gl == null){
            error = "OpenGL 1.0+ is not supported";
        }else if(gl2 == null){
            error = "OpenGL 2.0+ is not supported";
        }else if(gl3 == null){
            error = "OpenGL 3.0+ is not supported";
        }

        if(gl == null || gl2 == null || gl3 == null){
            throw new RuntimeException(error);
        }
    }

    public void prepare(){
        gl.glEnable(gl.GL_DEPTH_TEST);
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(backgroundColour.getRedChannel(), backgroundColour.getGreenChannel(), backgroundColour.getBlue(), 1);
    }

    private void createProjectionMatrix(int width, int height, @NotNull ViewPort viewPort) {
        float nearPlane = viewPort.getCamera().getNearPlane();
        float farPlane = viewPort.getCamera().getFarPlane();
        float fov = viewPort.getCamera().getFOV();
        logger.debug(String.format("Creating projection matrix with [FOV: %s | NEARPLANE:  %s | FARPLANE: %s]", fov, nearPlane, farPlane));

        float aspectRatio = (float) width / (float) height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        viewMatrix = new Matrix4f();

        viewMatrix.m00(x_scale);
        viewMatrix.m11(y_scale);
        viewMatrix.m22(-((farPlane + nearPlane) / frustum_length));
        viewMatrix.m23(-1);
        viewMatrix.m32(-((2 * nearPlane * farPlane) / frustum_length));
        viewMatrix.m33(0);
    }

    private void unbindTexturedModel(){
        enableCulling();
        gl2.glDisableVertexAttribArray(0);
        gl2.glDisableVertexAttribArray(1);
        gl2.glDisableVertexAttribArray(2);
        gl3.glBindVertexArray(0);
    }

    public void prepareSpatial(@NotNull Spatial spatial, @NotNull Material material){
        gl3.glBindVertexArray(spatial.getMesh().getID());
        gl2.glEnableVertexAttribArray(0);
        gl2.glEnableVertexAttribArray(1);
        gl2.glEnableVertexAttribArray(2);
        shader.loadNumberOfRows(material.getMultiTextureRows());
        if(material.isTransparency()){
           disableCulling();
        }
        shader.loadFakeLighting(material.isFakeLighting());
        shader.loadShineVariables(material.getShineDamper(), material.getReflectivity());
        gl.glActiveTexture(GL_TEXTURE0);
        gl.glBindTexture(GL_TEXTURE_2D, material.getID());

        Matrix4f transformationMatrix = Maths.createTransformationMatrix(spatial.getTransformation(),
                spatial.getRotation(), spatial.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadOffset(new Vector2f(material.getTextureXOffset(), material.getTextureYOffset()));
    }

    public void enableCulling(){
        gl.glEnable(GL_CULL_FACE);
        gl.glCullFace(GL_BACK);
    }

    public void disableCulling(){
        gl.glDisable(GL_CULL_FACE);
    }

    @Override
    public void init(ViewPort viewPort) {
        this.shader = new StaticShader(gl);

        gl.glEnable(GL_CULL_FACE);
        gl.glCullFace(GL_BACK);

        Vector2i windowSize = display.getWindowSize();
        createProjectionMatrix(windowSize.x, windowSize.y, viewPort);
        setViewPort(0, 0, windowSize.x, windowSize.y);

        this.skyboxRender = new SkyboxRender(textureManager, viewMatrix, gl, vaoManager);
        this.terrainShader = new TerrainShader(gl);
        this.terrainRenderer = new TerrainRenderer(terrainShader, viewMatrix);

        TerrainTexture backgroundTexture = new TerrainTexture(textureManager.loadTexture("grass3", PixelFormat.RGBA));
        TerrainTexture rTexture = new TerrainTexture(textureManager.loadTexture("mud", PixelFormat.RGBA));
        TerrainTexture gTexture = new TerrainTexture(textureManager.loadTexture("grassFlowers", PixelFormat.RGBA));
        TerrainTexture bTexture = new TerrainTexture(textureManager.loadTexture("path", PixelFormat.RGBA));
        TerrainTexture blendMap = new TerrainTexture(textureManager.loadTexture("blendMap", PixelFormat.RGBA));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        Terrain terrain = new Terrain(0, 0, vaoManager, texturePack, blendMap, "heightMap");
        terrains.add(terrain);

        this.shader.start();
        this.shader.loadTransformationMatrix(viewMatrix);
        this.shader.stop();
    }

    @Override
    public void setBackground(Colour colour) {
        Validation.checkNull(colour);
        this.backgroundColour = colour;
    }

    @Override
    public void setViewPort(int x, int y, int width, int height) {
        gl.glViewport(x, y, width, height);
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return viewMatrix;
    }

    @Override
    public void changeProjectionMatrix(Matrix4f matrix) {
        Validation.checkNull(matrix);
        this.viewMatrix = matrix;
    }

    @Override
    public void prepareRenderQueue(RenderQueue renderQueue) {
        for (int i = 0; i < renderQueue.size(); i++) {
            List<Spatial> spatialList = renderQueue.get(i).getChildren();
            for (Spatial spatial : spatialList) {
                if (spatial.getMesh() == null) {
                    throw new NullPointerException("Mesh is not set");
                }
                if (spatial.getMaterial() == null) {
                    throw new NullPointerException("Material is not set");
                }

                if (spatial.getMesh().getID() == -1) {
                    spatial.getMesh().setID(vaoManager.loadToVAO(spatial.getMesh()));
                }

                if (spatial.getMaterial().getID() == -1) {
                    Material material = spatial.getMaterial();
                    if (material.getMaterialBuffer() == null || material.getMaterialBuffer().capacity() == 0) {
                        throw new IllegalStateException("Material has null or zero texture buffer");
                    }
                    if (material.getMaterialWidth() == 0 || material.getMaterialHeight() == 0) {
                        throw new TextureDecoderException("Invalid material texture width or height");
                    }
                    int id = textureManager.loadTexture(material.getMaterialBuffer(), material.getMaterialWidth(), material.getMaterialHeight(), material.getMaterialFormat());
                    spatial.getMaterial().setID(id);
                }

            }
        }
        for(int i = 0; i < renderQueue.size(); i++){
            Node node = renderQueue.get(i);
            for(Spatial spatial : node.getChildren()) {
                addToQueue(spatial);
            }
        }
    }

    private void addToQueue(@NotNull Spatial entity){
        checkForDeprecation(entity.getClass());
        Material entityModel = entity.getMaterial();
        List<Spatial> batch = queue.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }else {
            List<Spatial> newBatch = new ArrayList<>();
            newBatch.add(entity);
            queue.put(entityModel, newBatch);
        }
    }

    private void checkForDeprecation(@NotNull Class<?> entity){
        AtomicBoolean isClassWarned = new AtomicBoolean(false);

        deprecatedList.forEach((aClass -> {
            if(aClass.equals(entity)){
                isClassWarned.set(true);
            }
        }));

        if(isClassWarned.get()){
            return;
        }

        if(entity.isAnnotationPresent(Deprecated.class)){
            logger.warn("Entity [" + entity.getPackageName() + "." + entity.getSimpleName() + "] is deprecated, try to use another or implement your own.");
        }

        deprecatedList.add(entity);
    }


    @Override
    public void render(@NotNull ViewPort viewPort) {
        prepare();

        Camera camera = viewPort.getCamera();
        List<Light> lights = viewPort.getLights();

        if(camera == null){
            throw new NullPointerException("Camera is null");
        }

        if(lights == null){
            throw new NullPointerException("Lights is null");
        }

        shader.start();
        shader.loadSkyColor(backgroundColour);
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        //glBegin(GL_LINES);
        for (Map.Entry<Material, List<Spatial>> entry : queue.entrySet()) {
            Material material = entry.getKey();
            List<Spatial> spatialList = entry.getValue();
            for(Spatial spatial : spatialList){
                if(spatial.getMesh().getID() == 0){
                    logger.warn("Spatial [" + spatial.getClass().toString() + "] have unloaded VAO id, skipping");
                    continue;
                }

                if(spatial.getMaterial().getID() == 0){
                    logger.warn("Spatial [" + spatial.getClass().toString() + "] have unloaded texture id, skipping");
                    continue;
                }

                prepareSpatial(spatial, material);
                logger.info(spatial.getMesh().getVertices().get(0));
                gl.glDrawElements(GL_TRIANGLES, spatial.getMesh().getCoordinatesSize(), GL_UNSIGNED_INT, 0);
                unbindTexturedModel();
            }
        }
        //glEnd();
        shader.stop();
        terrainShader.start();
        terrainShader.loadSkyColor(backgroundColour.getRed(), backgroundColour.getGreen(), backgroundColour.getBlue());
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        skyboxRender.render(viewPort.getCamera());
    }

    @Override
    public void cleanUp() {
        logger.info("Cleaning VAO list");
        this.vaoManager.cleanUp();
        logger.info("Cleaning textures");
        this.textureManager.cleanUp();
    }

    @Override
    public void takeScreenShot(String outputFile) {
        // TODO screenshot
    }

    @Override
    public void takeScreenShot(BufferedImage buffer) {
        // TODO screenshot
    }

    @Override
    public void takeScreenShot(ByteBuffer buffer) {
        // TODO screenshot
    }

    @Override
    public String getName() {
        return "OpenGL " + gl.glGetString(gl.GL_VERSION);
    }
}

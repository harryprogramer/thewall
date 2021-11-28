package thewall.engine.twilight.renderer;

import org.joml.Matrix4f;
import thewall.engine.twilight.display.GLFWDisplayManager;
import thewall.engine.twilight.entity.Spatial;
import thewall.engine.twilight.errors.NotImplementedException;
import thewall.engine.twilight.models.TexturedModel;
import thewall.engine.twilight.shaders.StaticShader;
import thewall.engine.twilight.texture.ModelTexture;
import thewall.engine.twilight.math.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

@Deprecated
public class EntityRenderer {
    private double lastUpdate = GLFW.glfwGetTime();


    private MasterRenderer projectionMatrix;

    private volatile GLFWDisplayManager GLFWDisplayManager;
    private volatile StaticShader staticShader;

    public EntityRenderer(@NotNull GLFWDisplayManager GLFWDisplayManager, @NotNull StaticShader staticShader, MasterRenderer projectionMatrix){
        this.GLFWDisplayManager = GLFWDisplayManager;
        this.staticShader = staticShader;
        this.projectionMatrix = projectionMatrix;
        staticShader.start();
        staticShader.loadProjectionMatrix(projectionMatrix.getProjectionMatrix());
        staticShader.stop();
    }

    public float random() {
        Random r = new Random();
        return .0f + r.nextFloat() * (.9f - .0f);
    }

    private void randomColorRender(){
        if(GLFW.glfwGetTime() - lastUpdate >= 1.0){
            glClearColor(random(), random(), random(), 1);
            lastUpdate = GLFW.glfwGetTime();

        }
    }

    public void render(@NotNull Map<TexturedModel, List<Spatial>> entities){

        for(TexturedModel model:entities.keySet()){
            prepareTexturedModel(model);
            List<Spatial> batch = entities.get(model);
            for(Spatial entity : batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL_TRIANGLES, model.getModel().getMesh().getCoordinatesSize(), GL_UNSIGNED_INT, 0);
            }

            unbindTexturedModel();
        }



        throw new NotImplementedException();
    }

    private void prepareTexturedModel(@NotNull TexturedModel model){
        // Model rawModel = model.getModel(); FIXME
        // GL30.glBindVertexArray(rawModel.getVaoID()); FIXME
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getModelTexture();
        staticShader.loadNumberOfRows(texture.getNumberOfRows());
        if(texture.isHasTransparency()){
            MasterRenderer.disableCulling();
        }
        staticShader.loadFakeLighting(texture.isUseFakeLighting());
        staticShader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL_TEXTURE_2D, model.getModelTexture().getID());
    }

    private void unbindTexturedModel(){
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(@NotNull Spatial entity){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getTransformation(),
                entity.getRotation(), entity.getScale());
        staticShader.loadTransformationMatrix(transformationMatrix);
        // staticShader.loadOffset(new Vector2f(entity.getTextureXOffset(), entity.getTextureYOffset())); FIXME
    }

    public void rebuildProjectionMatrix(){
        staticShader.start();
        staticShader.loadProjectionMatrix(projectionMatrix.getProjectionMatrix());
        staticShader.stop();
    }


}

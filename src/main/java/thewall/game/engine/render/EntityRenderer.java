package thewall.game.engine.render;

import thewall.game.engine.DisplayManager;
import thewall.game.engine.display.DisplayUtils;
import thewall.game.engine.display.Resolution;
import thewall.game.engine.entity.Entity;
import thewall.game.engine.models.RawModel;
import thewall.game.engine.models.TexturedModel;
import thewall.game.engine.shaders.StaticShader;
import thewall.game.engine.textures.ModelTexture;
import thewall.game.engine.utils.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer {
    private double lastUpdate = GLFW.glfwGetTime();


    private Matrix4f projectionMatrix;

    private volatile DisplayManager displayManager;
    private volatile StaticShader staticShader;

    public EntityRenderer(@NotNull DisplayManager displayManager, @NotNull StaticShader staticShader, Matrix4f projectionMatrix){
        this.displayManager = displayManager;
        this.staticShader = staticShader;
        staticShader.start();
        staticShader.loadProjectionMatrix(projectionMatrix);
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

    public void render(@NotNull Map<TexturedModel, List<Entity>> entities){
        for(TexturedModel model:entities.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL_TRIANGLES, model.getRawModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            }

            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(@NotNull TexturedModel model){
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getModelTexture();
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

    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
                entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        staticShader.loadTransformationMatrix(transformationMatrix);
    }


}

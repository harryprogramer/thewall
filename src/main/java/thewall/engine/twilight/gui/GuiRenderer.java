package thewall.engine.twilight.gui;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import thewall.engine.twilight.models.Loader;
import thewall.engine.twilight.models.RawModel;
import thewall.engine.twilight.shaders.GuiShader;
import thewall.engine.twilight.utils.Maths;

import java.util.List;

public class GuiRenderer {

    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer(@NotNull Loader loader){
        float[] positions = {-1, 1, -1, -1, 1, 1, 1 ,-1};
        this.quad = loader.loadToVAO(positions);
        this.shader = new GuiShader();
    }

    public void render(@NotNull List<GuiTexture> guis){
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for(GuiTexture texture : guis){
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTexture());
            Matrix4f matrix = Maths.createTransformationMatrix(texture.getPosition(), texture.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp(){
        shader.cleanUp();
    }
}

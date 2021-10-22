package thewall.engine.twilight.render;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import thewall.engine.twilight.models.RawModel;
import thewall.engine.twilight.shaders.TerrainShader;
import thewall.engine.twilight.terrain.Terrain;
import thewall.engine.twilight.textures.TerrainTexturePack;
import thewall.engine.twilight.utils.Maths;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class TerrainRenderer {
    private TerrainShader shader;
    private MasterRenderer renderer;

    public TerrainRenderer(TerrainShader terrainShader, @NotNull MasterRenderer projectionMatrix){
        this.shader = terrainShader;
        this.renderer = projectionMatrix;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix.getProjectionMatrix());
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(@NotNull List<Terrain> terrains){
        for(Terrain terrain : terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepareTerrain(@NotNull Terrain terrain){
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(1,0);
        bindTextures(terrain);
    }

    private void bindTextures(@NotNull Terrain terrain){
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL_TEXTURE_2D, texturePack.getRTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL_TEXTURE_2D, texturePack.getGTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL_TEXTURE_2D, texturePack.getBTexture().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
    }

    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(@NotNull Terrain terrain){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0,0,0,1);
        shader.loadTransformationMatrix(transformationMatrix);
    }

    public void rebuildMatrix(){
        shader.start();
        shader.loadProjectionMatrix(renderer.getProjectionMatrix());
        shader.connectTextureUnits();
        shader.stop();
    }
}

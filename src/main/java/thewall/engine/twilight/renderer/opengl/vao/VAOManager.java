package thewall.engine.twilight.renderer.opengl.vao;

import thewall.engine.twilight.models.Mesh;

public interface VAOManager {
    int loadToVAO(Mesh mesh);

    int loadToVAO(float[] positions, int dimensions);

    void freeMesh(Mesh mesh);

    void cleanUp();
}

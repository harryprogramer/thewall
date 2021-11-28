package thewall.engine.twilight.entity;

import com.google.common.primitives.Floats;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3f;
import thewall.engine.twilight.math.VectorMath;
import thewall.engine.twilight.models.Mesh;
import thewall.engine.twilight.utils.Validation;

import java.util.ArrayList;
import java.util.Arrays;

import static thewall.engine.twilight.math.VectorMath.*;

public class Box extends Spatial {
    Mesh boxMesh = new Mesh();

    public final Vector3f center = new Vector3f(0, 0, 0);
    public float xExtent, yExtent, zExtent;

    private final static Integer[] BOX_INDICES = {
            2,  1,  0,  3,  2,  0, // back
            6,  5,  4,  7,  6,  4, // right
            10,  9,  8, 11, 10,  8, // front
            14, 13, 12, 15, 14, 12, // left
            18, 17, 16, 19, 18, 16, // top
            22, 21, 20, 23, 22, 20  // bottom
    };

    private static final float[] GEOMETRY_NORMALS_DATA = {
            0,  0, -1,  0,  0, -1,  0,  0, -1,  0,  0, -1, // back
            1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0, // right
            0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1, // front
            -1,  0,  0, -1,  0,  0, -1,  0,  0, -1,  0,  0, // left
            0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0, // top
            0, -1,  0,  0, -1,  0,  0, -1,  0,  0, -1,  0  // bottom
    };

    private final static float[] BOX_TEXTURE_COORDINATES = {
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0

    };

    float[] STATIC_BOX_POSITIONS = {
            -0.5f,0.5f,0,
            -0.5f,-0.5f,0,
            0.5f,-0.5f,0,
            0.5f,0.5f,0,

            -0.5f,0.5f,1,
            -0.5f,-0.5f,1,
            0.5f,-0.5f,1,
            0.5f,0.5f,1,

            0.5f,0.5f,0,
            0.5f,-0.5f,0,
            0.5f,-0.5f,1,
            0.5f,0.5f,1,

            -0.5f,0.5f,0,
            -0.5f,-0.5f,0,
            -0.5f,-0.5f,1,
            -0.5f,0.5f,1,

            -0.5f,0.5f,1,
            -0.5f,0.5f,0,
            0.5f,0.5f,0,
            0.5f,0.5f,1,

            -0.5f,-0.5f,1,
            -0.5f,-0.5f,0,
            0.5f,-0.5f,0,
            0.5f,-0.5f,1
    };

    final int[] GEOMETRY_INDICES_DATA = {
            2,  1,  0,  3,  2,  0, // back
            6,  5,  4,  7,  6,  4, // right
            10,  9,  8, 11, 10,  8, // front
            14, 13, 12, 15, 14, 12, // left
            18, 17, 16, 19, 18, 16, // top
            22, 21, 20, 23, 22, 20  // bottom
    };

    float[] krzysztofTextureCoords = {
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0,
            0,0,
            0,1,
            1,1,
            1,0

    };

    public Box(Vector3f center , float xExtent, float yExtent, float zExtent) {
        Validation.checkNull(center);
        center.set(center);
        this.xExtent = xExtent;
        this.yExtent = yExtent;
        this.zExtent = zExtent;
        setScale(25);
        updateGeometry(center, xExtent, yExtent, zExtent);
    }

    protected final Vector3f @NotNull [] computeVertices() {
        Vector3f[] axes = {
                new Vector3f(1, 0, 0).mul(xExtent),
                new Vector3f(0, 1, 0).mul(yExtent),
                new Vector3f(0, 0, 1).mul(zExtent)
        };

        return new Vector3f[] {
                subtract(subtractLocal(subtractLocal(center,    axes[0]), axes[1]), axes[2]),
                add(subtractLocal(subtractLocal(center,         axes[0]), axes[1]), axes[2]),
                subtractLocal(addLocal(add(center,              axes[0]), axes[1]), axes[2]),
                subtractLocal(addLocal(subtract(center,         axes[0]), axes[1]), axes[2]),
                addLocal(subtractLocal(add(center,              axes[0]), axes[1]), axes[2]),
                addLocal(subtractLocal(subtract(center,         axes[0]), axes[1]), axes[2]),
                addLocal(addLocal(add(center,                   axes[0]), axes[1]), axes[2]),
                addLocal(addLocal(subtract(center,              axes[0]), axes[1]), axes[2]),
        };
    }

    public final void updateGeometry(Vector3f center, float x, float y, float z) {
        if (center != null) {this.center.set(center); }
        this.xExtent = x;
        this.yExtent = y;
        this.zExtent = z;

        /* vertices geometry */
        Vector3f[] v = computeVertices();
        float[] vertices = new float[] {
                v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z, v[3].x, v[3].y, v[3].z, // back
                v[1].x, v[1].y, v[1].z, v[4].x, v[4].y, v[4].z, v[6].x, v[6].y, v[6].z, v[2].x, v[2].y, v[2].z, // right
                v[4].x, v[4].y, v[4].z, v[5].x, v[5].y, v[5].z, v[7].x, v[7].y, v[7].z, v[6].x, v[6].y, v[6].z, // front
                v[5].x, v[5].y, v[5].z, v[0].x, v[0].y, v[0].z, v[3].x, v[3].y, v[3].z, v[7].x, v[7].y, v[7].z, // left
                v[2].x, v[2].y, v[2].z, v[6].x, v[6].y, v[6].z, v[7].x, v[7].y, v[7].z, v[3].x, v[3].y, v[3].z, // top
                v[0].x, v[0].y, v[0].z, v[5].x, v[5].y, v[5].z, v[4].x, v[4].y, v[4].z, v[1].x, v[1].y, v[1].z  // bottom
        };

        float[] vertices_f = {-1.0f, -1.0f, -2.0f, -2.0f, -2.0f, 1.0f, -1.0f, -1.0f, -1.0f, -3.0f, -1.0f, -3.0f, -2.0f, -2.0f, 1.0f, -1.0f, -3.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -3.0f, 1.0f, -3.0f, -3.0f, -1.0f, -3.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -3.0f, -3.0f, -1.0f, -1.0f, -1.0f, -2.0f, -3.0f, -1.0f, -3.0f, -3.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -3.0f, -1.0f, -1.0f, -3.0f, -1.0f, -3.0f, -1.0f, -1.0f, -2.0f, -3.0f, -3.0f, -1.0f, -1.0f, -3.0f, 1.0f, -2.0f, -2.0f, 1.0f};

        boxMesh.setVertices(Floats.asList(vertices_f));
        //boxMesh.setVertices(Floats.asList(vertices));

        /* indices geometry */
        boxMesh.setIndices(Arrays.asList(BOX_INDICES));

        /* geometry texture */
        ArrayList<Float> textureCoords = new ArrayList<>();
        for (float boxTextureCoordinate : BOX_TEXTURE_COORDINATES) {
            textureCoords.add(boxTextureCoordinate);
        }
        boxMesh.setTextureCoordinates(textureCoords);

        /* normals geometry */
        ArrayList<Float> normalsVertex = new ArrayList<>();
        for (float normal : GEOMETRY_NORMALS_DATA) {
            normalsVertex.add(normal);
        }
        boxMesh.setNormals(normalsVertex);

        this.boxMesh = new Mesh(Floats.asList(STATIC_BOX_POSITIONS), Arrays.asList(BOX_INDICES), textureCoords, normalsVertex);

        boxMesh.setName("Box");

        setMesh(boxMesh);
    }
}

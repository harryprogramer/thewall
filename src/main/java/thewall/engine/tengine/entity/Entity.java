package thewall.engine.tengine.entity;

import org.joml.Vector3f;
import thewall.engine.tengine.models.TexturedModel;
import lombok.Getter;

public class Entity {
    @Getter
    private final TexturedModel model;
    @Getter
    private final Vector3f position;
    @Getter
    private float rotX, rotY, rotZ;
    @Getter
    private float scale;

    public Entity(TexturedModel texturedModel, Vector3f vector3f, float rotX, float rotY, float rotZ, float scale) {
        this.model = texturedModel;
        this.position = vector3f;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz){
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

}

package thewall.engine.twilight.entity;

import lombok.Setter;
import org.joml.Vector3f;
import lombok.Getter;
import thewall.engine.twilight.terrain.Terrain;

public class Camera extends Entity {
    @Getter
    @Setter
    private float pitch;

    @Getter
    private Vector3f rotation = new Vector3f(0, -45, 0);


    public Camera(Vector3f vector3f, Terrain currentWorld) {
        super(null, vector3f, 0, currentWorld);
    }
}

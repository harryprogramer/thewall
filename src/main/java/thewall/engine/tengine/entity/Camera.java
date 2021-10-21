package thewall.engine.tengine.entity;

import lombok.Setter;
import org.joml.Vector3f;
import lombok.Getter;
import thewall.engine.tengine.models.TexturedModel;
import thewall.engine.tengine.terrain.Terrain;
import thewall.game.Game;
import thewall.game.TheWall;

import static org.lwjgl.glfw.GLFW.*;

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

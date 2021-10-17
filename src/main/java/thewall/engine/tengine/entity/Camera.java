package thewall.engine.tengine.entity;

import lombok.Setter;
import org.joml.Vector3f;
import lombok.Getter;
import thewall.game.Game;
import thewall.game.TheWall;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    @Getter
    private final Vector3f position = new Vector3f(300, 30, 700);
    @Getter
    @Setter
    private float pitch;

    @Getter
    private Vector3f rotation = new Vector3f(0, -45, 0);


}

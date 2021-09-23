package thewall.game.engine.entity;

import lombok.Data;
import org.lwjgl.util.vector.Vector3f;

@Data
public class Light {
    private final Vector3f position;
    private final Vector3f colour;
}

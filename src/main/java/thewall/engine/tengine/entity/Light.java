package thewall.engine.tengine.entity;

import lombok.Data;
import org.joml.Vector3f;

@Data
public class Light {
    private final Vector3f position;
    private final Vector3f colour;
}

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
    private final Vector3f rotation = new Vector3f(0, 0, 0);
    @Getter
    private float scale;

    public Entity(TexturedModel texturedModel, Vector3f vector3f, float scale) {
        this.model = texturedModel;
        this.position = vector3f;
        this.scale = scale;
    }

}

package thewall.engine.tengine.entity;

import org.joml.Vector3f;
import thewall.engine.tengine.models.TexturedModel;
import thewall.engine.tengine.terrain.Terrain;

@Deprecated
public class RawEntity extends Entity {
    public RawEntity(TexturedModel texturedModel, Vector3f vector3f, float scale, Terrain currentWorld) {
        super(texturedModel, vector3f, scale, currentWorld);
    }
}

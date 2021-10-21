package thewall.engine.twilight.entity;

import org.joml.Vector3f;
import thewall.engine.twilight.models.TexturedModel;
import thewall.engine.twilight.terrain.Terrain;

@Deprecated
public class RawEntity extends Entity {
    public RawEntity(TexturedModel texturedModel, Vector3f vector3f, float scale, Terrain currentWorld) {
        super(texturedModel, vector3f, scale, currentWorld);
    }
}

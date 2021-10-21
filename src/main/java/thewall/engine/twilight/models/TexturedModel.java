package thewall.engine.twilight.models;

import thewall.engine.twilight.textures.ModelTexture;
import lombok.Data;

@Data
public class TexturedModel {
    private final RawModel rawModel;
    private final ModelTexture modelTexture;
}

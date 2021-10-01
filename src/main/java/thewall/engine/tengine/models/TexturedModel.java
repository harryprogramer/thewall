package thewall.engine.tengine.models;

import thewall.engine.tengine.textures.ModelTexture;
import lombok.Data;

@Data
public class TexturedModel {
    private final RawModel rawModel;
    private final ModelTexture modelTexture;
}

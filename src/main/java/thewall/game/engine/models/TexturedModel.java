package thewall.game.engine.models;

import thewall.game.engine.textures.ModelTexture;
import lombok.Data;

@Data
public class TexturedModel {
    private final RawModel rawModel;
    private final ModelTexture modelTexture;
}

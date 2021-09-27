package thewall.game.tengine.models;

import thewall.game.tengine.textures.ModelTexture;
import lombok.Data;

@Data
public class TexturedModel {
    private final RawModel rawModel;
    private final ModelTexture modelTexture;
}

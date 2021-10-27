package thewall.engine.twilight.textures;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import thewall.engine.twilight.utils.Validation;

import java.nio.ByteBuffer;
import static thewall.engine.twilight.utils.Validation.*;

public class TextureData {
    private final int width;
    private final int height;
    private final ByteBuffer buffer;

    public TextureData(int width, int height, ByteBuffer buffer){
        checkNull(buffer, getClass());
        checkNull(width, getClass());
        checkNull(height, getClass());
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }

    public @NotNull int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public ByteBuffer getBuffer(){
        return buffer;
    }
}

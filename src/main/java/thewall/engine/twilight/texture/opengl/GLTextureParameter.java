package thewall.engine.twilight.texture.opengl;

public enum GLTextureParameter {
    TEXTURE_MAG_FILTER   (0x2800),
    TEXTURE_MIN_FILTER   (0x2801),
    TEXTURE_WRAP_S       (0x2802),
    TEXTURE_WRAP_T       (0x2803);

    public final int glCode;

    GLTextureParameter(int id){
        this.glCode = id;
    }
}

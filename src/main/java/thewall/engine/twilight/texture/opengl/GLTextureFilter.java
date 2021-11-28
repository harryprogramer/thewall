package thewall.engine.twilight.texture.opengl;

public enum GLTextureFilter {
    NEAREST                  (0x2600),
    LINEAR                   (0x2601),
    NEAREST_MIPMAP_NEAREST   (0x2700),
    LINEAR_MIPMAP_NEAREST    (0x2701),
    NEAREST_MIPMAP_LINEAR    (0x2702),
    LINEAR_MIPMAP_LINEAR     (0x2703);

    public final int glCode;

    GLTextureFilter(int id){
        this.glCode = id;
    }
}

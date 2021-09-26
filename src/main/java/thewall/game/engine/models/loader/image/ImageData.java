package thewall.game.engine.models.loader.image;

import java.nio.ByteBuffer;

public interface ImageData {

    /**
     * Get the last bit depth read from a TGA
     *
     * @return The last bit depth read
     */
    public int getDepth();

    /**
     * Get the last width read from a TGA
     *
     * @return Get the last width in pixels fread from a TGA
     */
    public int getWidth();

    /**
     * Get the last height read from a TGA
     *
     * @return Get the last height in pixels fread from a TGA
     */
    public int getHeight();

    /**
     * Get the last required texture width for a loaded image
     *
     * @return Get the ast required texture width for a loaded image
     */
    public int getTexWidth();

    /**
     * Get the ast required texture height for a loaded image
     *
     * @return Get the ast required texture height for a loaded image
     */
    public int getTexHeight();

    /**
     * Get the store image
     *
     * @return The stored image
     */
    public ByteBuffer getImageBufferData();

}
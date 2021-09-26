package thewall.game.engine.models.loader.texture;

import java.io.IOException;

public interface DeferredResource {

    /**
     * Load the actual resource
     *
     * @throws IOException Indicates a failure to load the resource
     */
    public void load() throws IOException;

    /**
     * Get a description of the resource to be loaded
     *
     * @return The description of the resource to be loaded
     */
    public String getDescription();
}

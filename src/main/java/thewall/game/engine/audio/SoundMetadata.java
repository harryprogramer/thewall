package thewall.game.engine.audio;

import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;


/**
 * Interface to fetch current audio metadata and information
 * <p></p>
 *
 * @see SoundManager
 * @see SoundMaster
 * @see SoundChannel
 * @author many
 * */

public interface SoundMetadata {
    @NonBlocking
    @NotNull
    @Contract(pure = true)
    Map<String, Object> getMetadata();

    @Contract(pure = true)
    float getVolume();

    @Contract(pure = true)
    float getPitch();

    @NotNull
    @NonBlocking
    @Contract(pure = true)
    String getSoundPath();

    @NotNull
    @Blocking
    @Contract(pure = true)
    File getSoundFile();
}

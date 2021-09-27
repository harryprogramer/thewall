package thewall.game.tengine.audio;

import org.jetbrains.annotations.NonBlocking;
import org.jetbrains.annotations.NotNull;

@NonBlocking
public interface SoundMaster {
    @NotNull
    @NonBlocking
    SoundChannel playBackground(float volume, float pitch, String file);

}

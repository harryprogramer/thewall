package thewall.game.engine.audio;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vector.Vector3f;

public interface VectorSoundMaster extends SoundMaster{
    @Override
    @NotNull SoundChannel playBackground(float volume, float pitch, String file);

    @NotNull SoundChannel playVector(Vector3f vector3f, float volume, float pitch, String file);
}

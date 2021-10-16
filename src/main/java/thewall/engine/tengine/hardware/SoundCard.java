package thewall.engine.tengine.hardware;

public interface SoundCard {
    String getDriverVersion();

    String getName();

    String getCodec();
}

package thewall.engine.tengine.hardware;

import java.util.List;

public interface Hardware {
    List<Disk> getDisks();

    List<Graphic> getGraphicCards();

    List<SoundCard> getSoundCards();

    List<PowerSource> getPowerSources();

    System getSystem();

    Graphic getUsedGraphic();

    Memory getMemory();

    Processor getProcessor();
}

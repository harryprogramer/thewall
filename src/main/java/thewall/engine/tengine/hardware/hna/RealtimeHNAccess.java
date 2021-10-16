package thewall.engine.tengine.hardware.hna;

import org.jetbrains.annotations.Blocking;
import thewall.engine.tengine.hardware.*;
import thewall.engine.tengine.hardware.System;

import java.util.List;

@Blocking
public class RealtimeHNAccess extends HardwareNativeAccess {
    @Override
    public List<Disk> getDisks() {
        return getHNADisks();
    }

    @Override
    public List<Graphic> getGraphicCards() {
        return getHNAGraphics();
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return getHNASoundCards();
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return getHNAPowerSource();
    }

    @Override
    public Graphic getUsedGraphic() {
        // TODO
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Memory getMemory() {
        return getHNAMemory();
    }

    @Override
    public Processor getProcessor() {
        return getHNAProcessor();
    }

    @Override
    public int getCurrentProcessID() {
        return getHNACurrentProcessID();
    }

    @Override
    public int getProcessCount() {
        return getHNAProcessCount();
    }

    @Override
    public long getSystemUptime() {
        return getHNASystemUpTime();
    }

    @Override
    public long getSystemBootTime() {
        return getHNASystemBootTime();
    }

    @Override
    public String getSystemName() {
        return getHNASystemName();
    }

    @Override
    public String getFamily() {
        return getHNASystemFamily();
    }

    @Override
    public String getManufacturer() {
        return getHNASystemManufacturer();
    }

    @Override
    public String getBuildNumber() {
        return getHNASystemBuildNumber();
    }

    @Override
    public String getBaseboardManufacturer() {
        return getHNABaseboardManufacturer();
    }

    @Override
    public String getBaseboardModel() {
        return getHNABaseboardModel();
    }

    @Override
    public String getBaseboardVersion() {
        return getHNABaseboardVersion();
    }

    @Override
    public String getBaseboardSerialNumber() {
        return getHNABaseboardSerialNumber();
    }

    @Override
    public String getVersion() {
        return getHNASystemVersion();
    }

    @Override
    public PlatformEnum getPlatform() {
        return getHNAPlatform();
    }
}

package thewall.engine.tengine.hardware.hna;

import org.jetbrains.annotations.NonBlocking;
import thewall.engine.tengine.errors.NotImplementedException;
import thewall.engine.tengine.hardware.*;
import thewall.engine.tengine.hardware.System;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;

@ThreadSafe
@NonBlocking
// TODO
public class IndexedASyncHNAccess extends HardwareNativeAccess{
    public IndexedASyncHNAccess(){
        throw new NotImplementedException();
    }

    @Override
    public List<Disk> getDisks() {
        return null;
    }

    @Override
    public List<Graphic> getGraphicCards() {
        return null;
    }

    @Override
    public List<SoundCard> getSoundCards() {
        return null;
    }

    @Override
    public List<PowerSource> getPowerSources() {
        return null;
    }

    @Override
    public System getSystem() {
        return null;
    }

    @Override
    public Graphic getUsedGraphic() {
        return null;
    }

    @Override
    public Memory getMemory() {
        return null;
    }

    @Override
    public Processor getProcessor() {
        return null;
    }

    @Override
    public int getCurrentProcessID() {
        return 0;
    }

    @Override
    public int getProcessCount() {
        return 0;
    }

    @Override
    public long getSystemUptime() {
        return 0;
    }

    @Override
    public long getSystemBootTime() {
        return 0;
    }

    @Override
    public String getSystemName() {
        return null;
    }

    @Override
    public String getFamily() {
        return null;
    }

    @Override
    public String getManufacturer() {
        return null;
    }

    @Override
    public String getBuildNumber() {
        return null;
    }

    @Override
    public String getBaseboardManufacturer() {
        return null;
    }

    @Override
    public String getBaseboardModel() {
        return null;
    }

    @Override
    public String getBaseboardVersion() {
        return null;
    }

    @Override
    public String getBaseboardSerialNumber() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public PlatformEnum getPlatform() {
        return null;
    }
}

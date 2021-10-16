package thewall.engine.tengine.hardware;

import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

import static org.junit.jupiter.api.Assertions.*;

class SystemTest {
    @Test
    void test(){
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        systemInfo.getHardware().getMemory();
        java.lang.System.out.println("Operating System: " + operatingSystem.toString());
    }
}
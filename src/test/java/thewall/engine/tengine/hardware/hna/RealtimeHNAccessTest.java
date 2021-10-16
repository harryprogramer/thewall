package thewall.engine.tengine.hardware.hna;

import org.junit.jupiter.api.Test;
import oshi.SystemInfo;

import static org.junit.jupiter.api.Assertions.*;

class RealtimeHNAccessTest {
    @Test
    void test(){
        RealtimeHNAccess hnAccess = new RealtimeHNAccess();
        SystemInfo systemInfo = new SystemInfo();
        System.out.println(hnAccess.getBaseboardModel());
    }

}
package thewall.engine.tengine.hardware.hna;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RealtimeHNAccessTest {
    @Test
    void test(){
        RealtimeHNAccess hnAccess = new RealtimeHNAccess();

        System.out.println(hnAccess.getSystem());
    }

}
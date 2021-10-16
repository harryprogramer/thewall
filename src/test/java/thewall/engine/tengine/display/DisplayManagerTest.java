package thewall.engine.tengine.display;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DisplayManagerTest {
    @SneakyThrows
    @Test
    void test(){
        long t1 = System.currentTimeMillis();

        Thread.sleep(500);

        float test = (System.currentTimeMillis() - t1) / 1000.0f;

        System.out.println(test);
    }
}
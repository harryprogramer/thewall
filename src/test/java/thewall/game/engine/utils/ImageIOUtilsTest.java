package thewall.game.engine.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ImageIOUtilsTest {
    @Test
    void test() throws IOException {
        ImageIOUtils.checkImageFormat("./res/texture/grass.png");
    }

}
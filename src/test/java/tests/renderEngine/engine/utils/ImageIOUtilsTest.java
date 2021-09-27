package tests.renderEngine.engine.utils;

import org.junit.jupiter.api.Test;
import thewall.game.tengine.utils.ImageIOUtils;

import java.io.IOException;

class ImageIOUtilsTest {
    @Test
    void test() throws IOException {
        ImageIOUtils.checkImageFormat("./res/texture/grass.png");
    }

}
package thewall.game.engine.utils;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;
import thewall.game.engine.entity.Camera;

import static org.junit.jupiter.api.Assertions.*;

class MathsTest {
    @Test
    void test(){
        System.out.println(Maths.createViewMatrix(new Camera()));
    }
}
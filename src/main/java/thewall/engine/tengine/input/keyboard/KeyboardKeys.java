package thewall.engine.tengine.input.keyboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import static org.lwjgl.glfw.GLFW.*;

public enum KeyboardKeys {

    W_KEY,
    S_KEY,
    A_KEY,
    D_KEY,
    SHIFT_KEY,
    F3_KEY,
    ESCAPE_KEY
    ;
    private static final Logger logger = LogManager.getLogger(KeyboardKeys.class);

    @Contract(pure = true)
    @Nullable
    public static KeyboardKeys keyToEnum(int code){
        switch (code){
            case GLFW_KEY_W -> {
                return KeyboardKeys.W_KEY;
            }

            case GLFW_KEY_D -> {
                return KeyboardKeys.D_KEY;
            }

            case GLFW_KEY_A -> {
                return KeyboardKeys.A_KEY;
            }

            case GLFW_KEY_S -> {
                return KeyboardKeys.S_KEY;
            }

            case GLFW_KEY_ESCAPE -> {
                return KeyboardKeys.ESCAPE_KEY;
            }


            default -> {
                logger.warn("Unknown key id: " + code);
                return null;
            }
        }
    }
}

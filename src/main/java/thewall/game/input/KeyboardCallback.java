package thewall.game.input;

import org.jetbrains.annotations.NotNull;
import thewall.engine.tengine.input.keyboard.KeyboardKeys;
import thewall.engine.tengine.input.keyboard.TKeyboardCallback;
import thewall.game.TheWall;

public class KeyboardCallback implements TKeyboardCallback {
    @Override
    public void invoke(@NotNull KeyboardKeys key, int scancode, int action, int mods) {
        switch (key){
            case ESCAPE_KEY -> {
                TheWall.getTheWall().stop();
            }
        }
    }
}

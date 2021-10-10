package thewall.game.input;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import thewall.engine.tengine.input.keyboard.KeyboardKeys;
import thewall.engine.tengine.input.keyboard.TKeyboardCallback;
import thewall.engine.tengine.input.mouse.Mouse;
import thewall.game.Game;
import thewall.game.TheWall;

public class KeyboardCallback implements TKeyboardCallback {
    @Override
    public void invoke(@NotNull KeyboardKeys key, int scancode, int action, int mods) {
        switch (key){
            case ESCAPE_KEY -> {
                Game.getGame().stop();
            }

            case F3_KEY -> {
                if(action == GLFW.GLFW_RELEASE) {
                    Mouse mouse = Game.getGame().input().getMouse();
                    if (mouse.isCursorDisabled()) {
                        mouse.showCursor();
                    } else {
                        mouse.disableCursor();
                    }
                }
            }
        }
    }
}

package thewall.engine.tengine.input;

import thewall.engine.tengine.input.keyboard.Keyboard;
import thewall.engine.tengine.input.mouse.Mouse;

public class InputProvider {
    private final Keyboard keyboard;
    private final Mouse mouse;

    public InputProvider(Keyboard keyboard, Mouse mouse){
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }
}

package thewall.engine.twilight.input;

import thewall.engine.twilight.input.gamepad.GamepadManager;
import thewall.engine.twilight.input.keyboard.Keyboard;
import thewall.engine.twilight.input.mouse.Mouse;

public class InputProvider implements Input{
    private final GamepadManager gamepadManager;
    private final Keyboard keyboard;
    private final Mouse mouse;

    public InputProvider(Keyboard keyboard, Mouse mouse, GamepadManager gamepadManager){
        this.gamepadManager = gamepadManager;
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    @Override
    public GamepadManager getGamepad(){
        return gamepadManager;
    }

    @Override
    public Keyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public Mouse getMouse() {
        return mouse;
    }
}

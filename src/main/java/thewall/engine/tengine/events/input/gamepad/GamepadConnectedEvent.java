package thewall.engine.tengine.events.input.gamepad;

import thewall.engine.tengine.events.Event;
import thewall.engine.tengine.events.EventType;
import thewall.engine.tengine.input.gamepad.Gamepad;


public final class GamepadConnectedEvent extends Event {
    private final Gamepad gamepad;

    public GamepadConnectedEvent(Gamepad gamepad) {
        super(true, EventType.INPUT);
        this.gamepad = gamepad;
    }

    public Gamepad getGamepad(){
        return gamepad;
    }

}

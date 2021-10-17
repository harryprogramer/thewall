package thewall.engine.tengine.events.input.gamepad;

import thewall.engine.tengine.events.Event;
import thewall.engine.tengine.events.EventType;
import thewall.engine.tengine.input.gamepad.Gamepad;
import thewall.engine.tengine.input.gamepad.GamepadNumber;

public class GamepadDisconnectedEvent extends Event {
    private final Gamepad gamepad;

    public GamepadDisconnectedEvent(Gamepad gamepad){
        super(true, EventType.INPUT);
        this.gamepad = gamepad;
    }

    public Gamepad getGamepad(){
        return gamepad;
    }
}

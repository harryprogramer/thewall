package thewall.game.events;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import thewall.engine.tengine.events.EngineEvent;
import thewall.engine.tengine.events.EventPriority;
import thewall.engine.tengine.events.EventType;
import thewall.engine.tengine.events.Listener;
import thewall.engine.tengine.events.input.gamepad.GamepadConnectedEvent;
import thewall.engine.tengine.events.input.gamepad.GamepadDisconnectedEvent;
import thewall.engine.tengine.input.gamepad.GamepadAxis;

@SuppressWarnings("unused")
public class GamepadEvent implements Listener {

    @SneakyThrows
    @EngineEvent(priority = EventPriority.NORMAL)
    public void onNewController(@NotNull GamepadConnectedEvent gamepadConnectedEvent){
        System.out.println("gamepad: " + gamepadConnectedEvent.getGamepad().getGamepadName());

    }

    @EngineEvent(priority = EventPriority.NORMAL)
    public void onDisconnect(@NotNull GamepadDisconnectedEvent gamepadDisconnectedEvent){
        System.out.println(gamepadDisconnectedEvent.getGamepad().getGamepadName());
    }
}

package thewall.game.events;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import thewall.engine.twilight.events.EngineEvent;
import thewall.engine.twilight.events.EventPriority;
import thewall.engine.twilight.events.EventType;
import thewall.engine.twilight.events.Listener;
import thewall.engine.twilight.events.input.gamepad.GamepadConnectedEvent;
import thewall.engine.twilight.events.input.gamepad.GamepadDisconnectedEvent;

@SuppressWarnings("unused")
public class GamepadEvent implements Listener {

    @SneakyThrows
    @EngineEvent(priority = EventPriority.NORMAL, type = EventType.INPUT)
    public void onNewController(@NotNull GamepadConnectedEvent gamepadConnectedEvent){
        System.out.println("gamepad: " + gamepadConnectedEvent.getGamepad().getGamepadName());
    }

    @EngineEvent(priority = EventPriority.NORMAL, type = EventType.INPUT)
    public void onDisconnect(@NotNull GamepadDisconnectedEvent gamepadDisconnectedEvent){
        System.out.println("gamepad disconnected: " + gamepadDisconnectedEvent.getGamepad().getGamepadName());
    }
}

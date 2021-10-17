package thewall.engine.tengine.input.gamepad;

import thewall.engine.tengine.input.gamepad.Gamepad;
import thewall.engine.tengine.input.gamepad.GamepadNumber;
import thewall.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GamepadLookupService {
    private final ConcurrentMap<GamepadNumber, Gamepad> gamepadMap = new ConcurrentHashMap<>();

    protected GamepadLookupService(){

    }

    public Gamepad createGamepad(int controller){
        Gamepad gamepad = new GLFWGamepad(controller);
        gamepadMap.put(GamepadNumber.numberToGamepadNumber(controller), gamepad);
        return gamepad;
    }

    public Gamepad getGamepad(int controller){
        return gamepadMap.get(GamepadNumber.numberToGamepadNumber(controller));
    }

    public void deleteGamepad(int controller){
        gamepadMap.remove(GamepadNumber.numberToGamepadNumber(controller));
    }
}

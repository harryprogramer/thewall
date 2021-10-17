package thewall.engine.tengine.input.gamepad;

import lombok.Getter;
import org.lwjgl.glfw.GLFWJoystickCallbackI;
import thewall.engine.tengine.TEngineApp;
import thewall.engine.tengine.events.input.gamepad.GamepadConnectedEvent;
import thewall.engine.tengine.events.input.gamepad.GamepadDisconnectedEvent;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWJoystickCallback implements GLFWJoystickCallbackI {
    @Getter
    private final GamepadLookupService gamepadLookupService = new GamepadLookupService();

    private final TEngineApp tEngineApp;

    public GLFWJoystickCallback(TEngineApp tEngineApp){
        this.tEngineApp = tEngineApp;

        List<Integer> controllers = getActiveControllers();
        for(int controller : controllers){
            gamepadLookupService.createGamepad(controller);
            this.tEngineApp.getEventManager().callEvent(new GamepadConnectedEvent(new GLFWGamepad(controller)));
        }
    }

    @Override
    public void invoke(int jid, int event) {
        jid++;
        if(event == GLFW_CONNECTED){
            tEngineApp.getEventManager().callEvent(new GamepadConnectedEvent(gamepadLookupService.createGamepad(jid)));
        }else if(event == GLFW_DISCONNECTED){
            tEngineApp.getEventManager().callEvent(new GamepadDisconnectedEvent(gamepadLookupService.getGamepad(jid)));
        }
    }

    private static List<Integer> getActiveControllers(){
        int[] controllerDictionary = {GLFW_JOYSTICK_1, GLFW_JOYSTICK_2, GLFW_JOYSTICK_3,
                GLFW_JOYSTICK_4, GLFW_JOYSTICK_5, GLFW_JOYSTICK_6, GLFW_JOYSTICK_7,
                GLFW_JOYSTICK_8, GLFW_JOYSTICK_9, GLFW_JOYSTICK_10, GLFW_JOYSTICK_11,
                GLFW_JOYSTICK_12, GLFW_JOYSTICK_13, GLFW_JOYSTICK_14, GLFW_JOYSTICK_15,
                GLFW_JOYSTICK_16};

        List<Integer> controllers = new ArrayList<>();

        for(int i = 0; i < 15; i++){
            if(glfwJoystickPresent(controllerDictionary[i])){
                controllers.add(i + 1);
            }
        }

        return controllers;
    }


}

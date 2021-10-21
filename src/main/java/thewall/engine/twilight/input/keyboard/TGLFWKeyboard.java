package thewall.engine.twilight.input.keyboard;

import thewall.engine.twilight.TwilightApp;

import static org.lwjgl.glfw.GLFW.*;

public class TGLFWKeyboard implements Keyboard{
    private final TwilightApp twilightApp;

    public TGLFWKeyboard(TwilightApp twilightApp){
        this.twilightApp = twilightApp;
    }

    @Override
    public boolean isKeyPressed(KeyboardKeys key) {
        return glfwGetKey(twilightApp.getDisplayManager().getWindow(), KeyboardKeys.enumToKey(key)) == GLFW_PRESS;
    }

    @Override
    public boolean isKeyReleased(KeyboardKeys key) {
        return glfwGetKey(twilightApp.getDisplayManager().getWindow(), KeyboardKeys.enumToKey(key)) == GLFW_RELEASE;
    }
}

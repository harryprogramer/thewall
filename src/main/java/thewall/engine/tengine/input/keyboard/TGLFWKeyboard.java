package thewall.engine.tengine.input.keyboard;

import thewall.engine.tengine.TEngineApp;
import static org.lwjgl.glfw.GLFW.*;

public class TGLFWKeyboard implements Keyboard{
    private final TEngineApp tEngineApp;

    public TGLFWKeyboard(TEngineApp tEngineApp){
        this.tEngineApp = tEngineApp;
    }

    @Override
    public boolean isKeyPressed(KeyboardKeys key) {
        return glfwGetKey(tEngineApp.getDisplayManager().getWindow(), KeyboardKeys.enumToKey(key)) == GLFW_PRESS;
    }

    @Override
    public boolean isKeyReleased(KeyboardKeys key) {
        return glfwGetKey(tEngineApp.getDisplayManager().getWindow(), KeyboardKeys.enumToKey(key)) == GLFW_RELEASE;
    }
}

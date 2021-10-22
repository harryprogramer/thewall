package thewall.engine.twilight.input.mouse;


import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import thewall.engine.twilight.TwilightApp;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.DoubleBuffer;

public class TGLFWMouse implements Mouse{
    private final TwilightApp app;

    public TGLFWMouse(@NotNull TwilightApp twilightApp){
        if(twilightApp.getWindowPointer() == 0){
            throw new NullPointerException("Null pointer");
        }

        this.app = twilightApp;
    }

    private static class CursorPositionImpl implements CursorPosition {
       private final double posX;
       private final double posY;

       public CursorPositionImpl(double posX, double posY){
           this.posX = posX;
           this.posY = posY;
       }

        @Override
        public double getXPos() {
            return posX;
        }

        @Override
        public double getYPos() {
            return posY;
        }
    }

    @Override
    public CursorPosition getCursorPosition() {
        DoubleBuffer xPos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer yPos = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(app.getWindowPointer(), xPos, yPos);
        return new CursorPositionImpl(xPos.get(0), yPos.get(0));
    }

    @Override
    public void setCursorPositionCallback(CursorPositionCallback callback) throws UnsupportedOperationException {
        glfwSetCursorPosCallback(app.getWindowPointer(), (window, xpos, ypos) -> callback.invoke(xpos, ypos));
    }

    @Override
    public void setCursorPosition(double posX, double posY) {
        glfwSetCursorPos(app.getWindowPointer(), posX, posY);
    }

    @Override
    public boolean getMouseKeyPress(MouseButtons button) {
        return glfwGetMouseButton(app.getWindowPointer(), MouseButtons.buttonToEnum(button)) == GLFW_PRESS;
    }

    @Override
    public boolean getMouseKeyReleased(MouseButtons button) {
        return glfwGetMouseButton(app.getWindowPointer(), MouseButtons.buttonToEnum(button)) == GLFW_RELEASE;
    }


    @Override
    public void createMouseKeyCallback(TMouseCallback callback) {
        glfwSetMouseButtonCallback(app.getWindowPointer(), (window, button, action, mods) -> callback.invoke(button, action, mods));
    }

    @Override
    public void hideCursor() {
        glfwSetInputMode(app.getWindowPointer(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    @Override
    public void showCursor() {
        glfwSetInputMode(app.getWindowPointer(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    @Override
    public boolean isCursorDisabled() {
        return glfwGetInputMode(app.getWindowPointer(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED;
    }

    @Override
    public void disableCursor() {
        glfwSetInputMode(app.getWindowPointer(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    @Override
    public boolean isCursorHidden() {
        return glfwGetInputMode(app.getWindowPointer(), GLFW_CURSOR) == GLFW_CURSOR_HIDDEN;
    }
}

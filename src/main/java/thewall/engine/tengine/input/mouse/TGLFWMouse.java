package thewall.engine.tengine.input.mouse;


import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import thewall.engine.tengine.TEngineApp;
import static org.lwjgl.glfw.GLFW.*;
import java.nio.DoubleBuffer;

public class TGLFWMouse implements Mouse{
    private final TEngineApp app;

    public TGLFWMouse(@NotNull TEngineApp tEngineApp){
        if(tEngineApp.getDisplayManager().getWindow() == 0){
            throw new NullPointerException("Null pointer");
        }

        this.app = tEngineApp;
    }

    private record CursorPositionImpl(double posX, double posY) implements CursorPosition {
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
        glfwGetCursorPos(app.getDisplayManager().getWindow(), xPos, yPos);
        return new CursorPositionImpl(xPos.get(0), yPos.get(0));
    }

    @Override
    public void setCursorPositionCallback(CursorPositionCallback callback) throws UnsupportedOperationException {
        glfwSetCursorPosCallback(app.getDisplayManager().getWindow(), (window, xpos, ypos) -> callback.invoke(xpos, ypos));
    }

    @Override
    public void setCursorPosition(double posX, double posY) {

    }

    @Override
    public boolean getMouseKeyPress(MouseButtons button) {
        return glfwGetMouseButton(app.getDisplayManager().getWindow(), MouseButtons.buttonToEnum(button)) == GLFW_PRESS;
    }

    @Override
    public boolean getMouseKeyReleased(MouseButtons button) {
        return glfwGetMouseButton(app.getDisplayManager().getWindow(), MouseButtons.buttonToEnum(button)) == GLFW_RELEASE;
    }


    @Override
    public void createMouseKeyCallback(TMouseCallback callback) {
        glfwSetMouseButtonCallback(app.getDisplayManager().getWindow(), (window, button, action, mods) -> callback.invoke(button, action, mods));
    }

    @Override
    public void hideCursor() {
        glfwSetInputMode(app.getDisplayManager().getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    @Override
    public void showCursor() {
        glfwSetInputMode(app.getDisplayManager().getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }
}

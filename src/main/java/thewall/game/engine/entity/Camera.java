package thewall.game.engine.entity;

import org.lwjgl.BufferUtils;
import thewall.game.Game;
import lombok.Getter;
import org.lwjgl.util.vector.Vector3f;
import thewall.game.engine.debugger.console.DebugConsole;

import java.nio.DoubleBuffer;
import java.text.DecimalFormat;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    @Getter
    private final Vector3f position = new Vector3f(250, 2, 250);
    @Getter
    private float yaw, roll, pitch;

    private static final float MOUSESENSTIVITY = 0.2f;

    private double previousTime = glfwGetTime();

    public Camera(){}


    @Deprecated
    /**
     * Simple movement for camera
     *
     * @deprecated
     * */
    public void move(){
        if(glfwGetInputMode(Game.getDisplayManager().getWindow(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED) {
            DoubleBuffer xBuffer = BufferUtils.createDoubleBuffer(1);
            DoubleBuffer yBuffer = BufferUtils.createDoubleBuffer(1);
            glfwGetCursorPos(Game.getDisplayManager().getWindow(), xBuffer, yBuffer);
            xBuffer.rewind();
            yBuffer.rewind();
            double x = xBuffer.get(0);
            double y = yBuffer.get(0);

            pitch = (float) (MOUSESENSTIVITY * y);
            yaw = (float) (MOUSESENSTIVITY * x);
            if(glfwGetTime() - previousTime > 0.5){
                Game.getDebug().info(String.format("Mouse X: [%s]", new DecimalFormat("##.##").format(yaw)));
                Game.getDebug().info(String.format("Mouse Y: [%s]", new DecimalFormat("##.##").format(pitch)));
                previousTime = glfwGetTime();
            }
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_W) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z -= 0.5f;
            }
            position.z -= 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_D) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x += 0.5f;
            }
            position.x += 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_A) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x -= 0.5f;
            }
            position.x -= 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_S) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z += 0.5f;
            }
            position.z += 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.y -= 0.5f;
            }
            position.y -= 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.y += 0.5f;
            }
            position.y += 0.1f;
        }
    }
}

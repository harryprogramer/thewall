package thewall.game.tengine.entity;

import org.joml.Vector3f;
import lombok.Getter;
import org.lwjgl.BufferUtils;
import thewall.game.Game;

import java.nio.DoubleBuffer;
import java.text.DecimalFormat;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    @Getter
    private final Vector3f position = new Vector3f(300, 30, 700);
    @Getter
    private float yaw, roll, pitch;

    private static final float MOUSESENSTIVITY = 0.2f;

    private double previousTime = glfwGetTime();

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;


    public Camera(Player player){}


    @Deprecated
    /**
     * Simple movement for camera
     *
     * @deprecated
     * */
    public void move(){
        /*
        if(glfwGetInputMode(Game.getDisplayManager().getWindow(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED && glfwGetWindowAttrib(Game.getDisplayManager().getWindow(), GLFW_FOCUSED) == 1) {
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

         */

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_UP) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z -= 0.5f;
            }
            position.z -= 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_RIGHT) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x += 0.5f;
            }
            position.x += 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(),  GLFW_KEY_LEFT) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x -= 0.5f;
            }
            position.x -= 0.1f;
        }

        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_DOWN) == GLFW_PRESS){
            if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z += 0.5f;
            }
            position.z += 0.1f;
        }
        /*
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

         */
    }

    private void calculateZoom(){
        //float zoomLevel =
    }

}

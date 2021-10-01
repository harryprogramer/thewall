package thewall.engine.tengine.entity;

import lombok.Setter;
import org.joml.Vector3f;
import lombok.Getter;
import thewall.game.Game;
import thewall.game.TheWall;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    @Getter
    private final Vector3f position = new Vector3f(300, 30, 700);
    @Getter
    @Setter
    private float yaw, roll, pitch;

    private static final float MOUSESENSTIVITY = 0.2f;

    private double previousTime = glfwGetTime();

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;



    @Deprecated
    /**
     * Simple movement for camera
     *
     * @deprecated
     * */
    public void move(){
        /*
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

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(),  GLFW_KEY_UP) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z -= 0.5f;
            }
            position.z -= 0.1f;
        }

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(),  GLFW_KEY_RIGHT) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x += 0.5f;
            }
            position.x += 0.1f;
        }

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(),  GLFW_KEY_LEFT) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.x -= 0.5f;
            }
            position.x -= 0.1f;
        }

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_DOWN) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.z += 0.5f;
            }
            position.z += 0.1f;
        }

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.y -= 0.5f;
            }
            position.y -= 0.1f;
        }

        if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS){
            if(glfwGetKey(TheWall.getTheWall().getDisplayManager().getWindow(), GLFW_KEY_LEFT_CONTROL) == GLFW_PRESS){
                position.y += 0.5f;
            }
            position.y += 0.1f;
        }


    }

    private void calculateZoom(){
        //float zoomLevel =
    }

}

package thewall.engine.tengine.entity;

import lombok.Getter;
import org.joml.Vector3f;
import thewall.game.Game;
import thewall.engine.tengine.display.DisplayManager;
import thewall.engine.tengine.models.TexturedModel;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;
    boolean isOnAir = false;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    @Getter
    private final Camera camera;

    public Player(TexturedModel texturedModel, Vector3f vector3f, float rotX, float rotY, float rotZ, float scale) {
        super(texturedModel, vector3f, rotX, rotY, rotZ, scale);
        this.camera = new Camera();
    }

    public void tick(){
        //checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if(super.getPosition().y < TERRAIN_HEIGHT){
            upwardsSpeed = 0;
            isOnAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
        //camera.getPosition().x = getPosition().x;
        //camera.getPosition().y = getPosition().y + 5;
        //camera.getPosition().z = getPosition().z;
    }

    private void jump(){
        if(!isOnAir) {
            this.upwardsSpeed = JUMP_POWER;
            isOnAir = true;
        }
    }

    public void checkInputs(){
        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_W) == GLFW_PRESS){
            this.currentSpeed = RUN_SPEED;
        }else if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_S) == GLFW_PRESS){
            this.currentSpeed = -RUN_SPEED;
        }else {
            this.currentSpeed = 0;
        }



        if(glfwGetKey(Game.getDisplayManager().getWindow(), GLFW_KEY_SPACE) == GLFW_PRESS){
            jump();
        }
    }
}

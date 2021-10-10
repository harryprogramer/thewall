package thewall.engine.tengine.entity;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import thewall.engine.tengine.input.InputProvider;
import thewall.engine.tengine.input.keyboard.KeyboardKeys;
import thewall.engine.tengine.input.mouse.CursorPosition;
import thewall.engine.tengine.display.DisplayManager;
import thewall.engine.tengine.models.TexturedModel;
import thewall.game.Game;
import thewall.game.TheWall;

public class Player extends Entity {
    private final static Logger logger = LogManager.getLogger(Player.class);

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;
    boolean isOnAir = false;

    private float moveSpeed = 0.2f, mouseSensitivity = 0.15f;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private double newMouseX, newMouseY, oldMouseX = 0, oldMouseY = 0;

    @Getter
    private final Camera camera;

    public Player(TexturedModel texturedModel, Vector3f vector3f, float rotX, float rotY, float rotZ, float scale) {
        super(texturedModel, vector3f, rotX, rotY, rotZ, scale);
        this.camera = new Camera();
    }

    public void tick(){
        if(Game.getGame().input().getMouse().isCursorDisabled()) {
            checkInputs();
        }

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
        InputProvider input = Game.getGame().input();
        CursorPosition pos = input.getMouse().getCursorPosition();
        newMouseX = pos.getXPos();
        newMouseY = pos.getYPos();
        float x = (float) Math.sin(Math.toRadians(camera.getRotation().y)) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(camera.getRotation().y)) * moveSpeed;

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.A_KEY)){
            camera.getPosition().add(new Vector3f(-z, 0, x));
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.D_KEY)){
            camera.getPosition().add(new Vector3f(z, 0, -x));
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.W_KEY)){
            camera.getPosition().add(new Vector3f(-x, 0, -z));
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.S_KEY)){
            camera.getPosition().add(new Vector3f(x, 0, z));
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.SPACE_KEY)){
            camera.getPosition().add(0, moveSpeed, 0);
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.LEFT_SHIFT_KEY)){
            camera.getPosition().add(new Vector3f(0, -moveSpeed, 0));
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.LEFT_CONTROL_KEY)){
            moveSpeed = 0.6f;
        }else {
            moveSpeed = 0.2f;
        }

        if(input.getKeyboard().isKeyPressed(KeyboardKeys.SPACE_KEY)){
            jump();
        }

        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);


        Vector3f finalRotation = camera.getRotation();
        finalRotation.add(new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));

        if(finalRotation.x >= 90){
            finalRotation.x = 90f;
        }else if(finalRotation.x < -90){
            finalRotation.x = -90f;
        }

        camera.getRotation().set(finalRotation);

        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }
}

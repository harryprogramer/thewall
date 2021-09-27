package thewall.game;

import thewall.game.tengine.TEngineApp;
import thewall.game.tengine.entity.Camera;
import thewall.game.tengine.input.keyboard.KeyboardKeys;

public class TheWall extends TEngineApp {
    Camera camera;
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void enginePulse() {
        //rndrProcessTerrain(new Terrain(1, 0, getLoader(), new ModelTexture(getLoader().loadTexture("grass3", GL_RGBA))));

    }

    public static void main(String[] args) throws InterruptedException {
        TheWall theWall = new TheWall();
        theWall.camera = new Camera();
        theWall.rndrProcessCamera(theWall.camera);
        //theWall.rndrProcessEntity(new Entity(new TexturedModel(OBJLoader.loadObjModel("models/tree", theWall.getLoader()), new ModelTexture(the.loadTexture("stallTexture", GL_RGBA)));, new Vector3f(250, 2, 250), 0, 180, 0, 1););
        theWall.startEngine();
        theWall.setKeyboardCallback((key, scancode, action, mods) -> {
            if(key == KeyboardKeys.W_KEY){
                theWall.camera.getPosition().x -= 0.1f;
            }
        });
    }
}

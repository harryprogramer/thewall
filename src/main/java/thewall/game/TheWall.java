package thewall.game;

import org.lwjgl.util.vector.Vector3f;
import thewall.game.engine.PogEngineApp;
import thewall.game.engine.entity.Camera;
import thewall.game.engine.entity.Entity;
import thewall.game.engine.input.KeyboardKeys;
import thewall.game.engine.models.TexturedModel;
import thewall.game.engine.models.obj.thinmatrix.OBJLoader;
import thewall.game.engine.terrain.Terrain;
import thewall.game.engine.textures.ModelTexture;

import static org.lwjgl.opengl.GL11.GL_RGBA;

public class TheWall extends PogEngineApp {
    Camera camera;
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void enginePulse() {
        rndrProcessTerrain(new Terrain(1, 0, getLoader(), new ModelTexture(getLoader().loadTexture("grass3", GL_RGBA))));

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

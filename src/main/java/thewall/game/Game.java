package thewall.game;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thewall.engine.tengine.TEngineApp;
import thewall.game.input.KeyboardCallback;

public class Game {
    @Getter
    private final static TheWall game = new TheWall();

    private final static Logger logger = LogManager.getLogger(Game.class);

    public static void main(String[] args) {
        TEngineApp.init();
        try{
            game.getDebugConsole().showConsole();
            TEngineApp.startApp(game);
        }catch (Exception e){
            logger.fatal("Cannot start game, fatal runtime error", e);
            System.exit(1);
            return;
        }
        game.input().getMouse().disableCursor();
        game.enableVSync();
        game.setKeyboardCallback(new KeyboardCallback());
        game.setFPSLimit(200);

    }
}

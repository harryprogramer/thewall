package thewall.game.engine.scripting;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thewall.game.engine.scripting.api.ApiFactory;

import java.io.File;
import java.io.FileNotFoundException;

class JSScriptManagerTest {

    ScriptPlugin scriptPlugin;

    @BeforeEach
    public void createRuntime() throws FileNotFoundException {
        ScriptManager scriptManager = new JSScriptManager();
        scriptManager.createRuntime();
        scriptPlugin = scriptManager.executeScript(new File("./test.js"));
    }

    @Test
    public void executeScript() {
        System.out.println(scriptPlugin.getName() );
        scriptPlugin.start();
    }
}
package thewall.game.tengine.scripting.api.console;

public class JScriptConsole implements ScriptConsole{
    @Override
    public void log(String content) {
        System.out.println(content);
    }
}
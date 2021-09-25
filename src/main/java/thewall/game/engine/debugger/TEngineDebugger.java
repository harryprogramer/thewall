package thewall.game.engine.debugger;

import thewall.game.engine.debugger.console.ConsoleOutProxy;
import thewall.game.engine.debugger.console.DebugConsole;

public final class TEngineDebugger {
    private TEngineDebugger(){
        throw new IllegalStateException("This class cannot have instance");
    }

    public static void setPrintProxyDebugger(DebugConsole console){
        ConsoleOutProxy.setConsole(console);
        System.setOut(ConsoleOutProxy.getInstance());
    }
}

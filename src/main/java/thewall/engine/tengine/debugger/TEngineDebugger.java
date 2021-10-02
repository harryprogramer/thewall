package thewall.engine.tengine.debugger;

import thewall.engine.tengine.debugger.console.ConsoleOutProxy;
import thewall.engine.tengine.debugger.console.DebugConsole;

public final class TEngineDebugger {
    private TEngineDebugger(){
        throw new IllegalStateException("This class cannot have instance");
    }

    public static void setPrintProxyDebugger(DebugConsole console){
        ConsoleOutProxy.setConsole(console);
        System.setOut(ConsoleOutProxy.getInstance());
    }

}

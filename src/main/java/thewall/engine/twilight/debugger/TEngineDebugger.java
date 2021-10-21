package thewall.engine.twilight.debugger;

import lombok.Getter;
import thewall.engine.twilight.debugger.console.ConsoleOutProxy;
import thewall.engine.twilight.debugger.console.DebugConsole;

import java.io.PrintStream;

public final class TEngineDebugger {
    @Getter
    private static PrintStream nativeOut = null;

    private TEngineDebugger(){
        throw new IllegalStateException("This class cannot have instance");
    }

    public static void setPrintProxyDebugger(DebugConsole console){
        ConsoleOutProxy.setConsole(console);
        nativeOut = System.out;
        System.setOut(ConsoleOutProxy.getInstance());
    }

}

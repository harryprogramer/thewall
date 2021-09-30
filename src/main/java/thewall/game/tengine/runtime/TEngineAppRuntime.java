package thewall.game.tengine.runtime;

import thewall.game.tengine.TEngineApp;

public class TEngineAppRuntime extends TRuntime<TEngineApp> {
    public TEngineAppRuntime() {
        super("TEngine Game App Runtime");
    }

    @Override
    protected void start(TEngineApp program) {
        program.startEngine();
    }
}

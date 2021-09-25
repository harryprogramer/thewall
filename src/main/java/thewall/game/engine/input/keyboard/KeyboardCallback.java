package thewall.game.engine.input.keyboard;

@FunctionalInterface
public interface KeyboardCallback {
    void invoke(KeyboardKeys key, int scancode, int action, int mods);
}

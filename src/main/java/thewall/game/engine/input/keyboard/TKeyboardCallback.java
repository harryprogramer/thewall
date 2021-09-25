package thewall.game.engine.input.keyboard;

@FunctionalInterface
public interface TKeyboardCallback {
    void invoke(KeyboardKeys key, int scancode, int action, int mods);
}

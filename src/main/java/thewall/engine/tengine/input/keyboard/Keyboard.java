package thewall.engine.tengine.input.keyboard;

public interface Keyboard {
    boolean isKeyPressed(KeyboardKeys key);

    boolean isKeyReleased(KeyboardKeys key);
}

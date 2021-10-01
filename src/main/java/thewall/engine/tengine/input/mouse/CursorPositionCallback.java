package thewall.engine.tengine.input.mouse;

@FunctionalInterface
public interface CursorPositionCallback {
    void invoke(double xpos, double ypos);
}

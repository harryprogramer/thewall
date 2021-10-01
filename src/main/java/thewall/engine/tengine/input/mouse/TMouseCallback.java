package thewall.engine.tengine.input.mouse;

@FunctionalInterface
public interface TMouseCallback {
    void invoke(int button, int action, int mods);
}

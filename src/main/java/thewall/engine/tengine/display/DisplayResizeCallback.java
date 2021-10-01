package thewall.engine.tengine.display;

@FunctionalInterface
public interface DisplayResizeCallback {
    void invoke(int x, int y);
}

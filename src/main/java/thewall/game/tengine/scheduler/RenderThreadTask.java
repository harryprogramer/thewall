package thewall.game.tengine.scheduler;

@FunctionalInterface
public interface RenderThreadTask extends Runnable{
    @Override
    void run();

    // TODO
}

package thewall.game.engine.scheduler;

@FunctionalInterface
public interface RenderThreadTask extends Runnable{
    @Override
    void run();
}

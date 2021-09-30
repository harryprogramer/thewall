package thewall.game.tengine.runtime;

import lombok.Getter;

public abstract class TRuntime<P> {
    @Getter
    private final String name;

    public TRuntime(String name){
        this.name = name;
    }

    public void execute(P program){
        start(program);
    }

    protected abstract void start(P program);
}

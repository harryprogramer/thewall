package thewall.engine.tengine.runtime;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class AbstractRuntime<P> {
    @Getter
    private final String name;

    private final static Logger logger = LogManager.getLogger(AbstractRuntime.class);

    public AbstractRuntime(String name){
        this.name = name;
    }

    public void execute(P program){
        logger.info("Starting runtime for [" + name + "]");
        Objects.requireNonNull(program);
        start(program);

    }

    public void forceStop(){
        logger.info("Stopping runtime for [" + name + "]");
        executeTask(this::stop);
    }

    public boolean checkThread(){
        return Thread.currentThread() == getThread();
    }

    protected abstract Thread getThread();

    public abstract void executeTask(Runnable runnable);

    protected abstract void start(P program);

    protected abstract void stop();
}

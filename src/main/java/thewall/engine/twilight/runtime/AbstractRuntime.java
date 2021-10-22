package thewall.engine.twilight.runtime;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class AbstractRuntime<P> {
    @Getter
    private final String name;

    private P program;

    private final static Logger logger = LogManager.getLogger(AbstractRuntime.class);

    public AbstractRuntime(String name){
        this.name = name;
    }

    public void execute(P program){
        logger.info("Starting runtime for [" + name + "]");
        Objects.requireNonNull(program);
        if(this.program != null){
            throw new RuntimeException("Runtime is already used, active program: " + program.getClass().getSimpleName());
        }

        TwilightRuntimeService.registerActiveRuntime(program, this);

        this.program = program;

        try {
            start(program);
        }catch (Exception e){
            forceStop();
            throw e;
        }
    }

    @SneakyThrows
    public void forceStop(){
        if(this.program == null){
            throw new RuntimeException("Runtime is unused");
        }
        logger.info("Stopping runtime for [" + name + "]");
        TwilightRuntimeService.deleteActiveRuntime(this.program);
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

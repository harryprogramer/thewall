package thewall.engine.tengine.runtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TEngineRuntime {
    private static final Logger logger = LogManager.getLogger(TEngineRuntime.class);
    private static final HashMap<Class<?>, Class<? extends AbstractRuntime<?>>> runtimes = new HashMap<>();

    public static void registerRuntime(Class<?> program, Class<? extends AbstractRuntime<?>> tRuntime){
        if(runtimes.containsKey(program)){
            throw new IllegalStateException("Runtime [" + tRuntime.getName() + "] is already registered");
        }
        runtimes.put(program, tRuntime);
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <K> AbstractRuntime<K> findRuntime(Class<K> program){
        Class<? extends AbstractRuntime<?>> runtime;

        runtime = runtimes.get(program);
        if(runtime == null){
            return null;
        }

        try {
            return (AbstractRuntime<K>) runtime.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.warn("Cannot create runtime", e);
        }

       return null;
    }
}

package thewall.game.tengine.runtime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TEngineRuntime {
    private static final Logger logger = LogManager.getLogger(TEngineRuntime.class);
    private static final HashMap<Class<?>, Class<? extends TRuntime<?>>> runtimes = new HashMap<>();

    public static void registerRuntime(Class<?> program, Class<? extends TRuntime<?>> tRuntime){
        if(runtimes.containsKey(program)){
            throw new IllegalStateException("Runtime [" + tRuntime.getName() + "] is already registered");
        }
        runtimes.put(program, tRuntime);
    }

    public static @Nullable TRuntime<?> findRuntime(Class<?> program){
        try {
            return runtimes.get(program).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

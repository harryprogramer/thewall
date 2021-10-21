package thewall.engine.twilight.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TEventManager implements EventManager {
    private final ExecutorService asyncExecutor = Executors.newFixedThreadPool(50);
    private final static Logger logger = LogManager.getLogger(TEventManager.class);
    private final ArrayList<Listener> listenerList = new ArrayList<>();


    @Override
    public void registerEvents(Listener listener) {
        Objects.requireNonNull(listener);
        listenerList.add(listener);
    }

    @Override
    public void unregisterEvents(Listener listener) {
        Objects.requireNonNull(listener);
        listenerList.remove(listener);
    }

    @Override
    public void callEvent(Event event) {
        long startTime = System.currentTimeMillis();
        try {
            for (Listener listener : listenerList) {
                List<Method> events = getMethodsAnnotatedWith(listener, event.getEventType());
                for (Method method : events) {
                    if (method.getParameterTypes()[0] == event.getClass()) {
                        if(event.isAsynchronous()){
                            asyncExecutor.submit(() -> method.invoke(listener, event));
                            return;
                        }
                        method.invoke(listener, event);
                        double endTime = (System.currentTimeMillis() - startTime) / 1000.0;
                        System.out.println("Event call time: " + endTime);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.fatal("Unexpected error in calling an event, " + e);
        }
    }

    private static @NotNull List<Method> getMethodsAnnotatedWith(final @NotNull Listener type, EventType eventType) {
        final List<Method> methods = new ArrayList<>();
        Class<?> klass = type.getClass();
        while (klass != Object.class) {
            for (final Method method : klass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(EngineEvent.class)) {
                    if(method.getAnnotation(EngineEvent.class).type() != eventType){
                        continue;
                    }
                    methods.add(method);
                }
            }
            klass = klass.getSuperclass();
        }
        return methods;
    }
}

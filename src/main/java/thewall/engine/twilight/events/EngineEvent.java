package thewall.engine.twilight.events;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EngineEvent {
    EventType type();

    EventPriority priority() default EventPriority.NORMAL;
}

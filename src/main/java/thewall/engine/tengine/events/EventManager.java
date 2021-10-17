package thewall.engine.tengine.events;

import thewall.engine.tengine.TEngineApp;

public interface EventManager {
    void registerEvents(Listener listener);

    void unregisterEvents(Listener listener);

    void callEvent(Event event);
}

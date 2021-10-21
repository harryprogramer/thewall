package thewall.engine.tengine.events;

import org.jetbrains.annotations.NotNull;

public abstract class Event implements Comparable<Event> {
    private final boolean async;
    private final EventType eventType;
    private String name;

    public Event(boolean isASync, EventType eventType){
        this.async = isASync;
        this.eventType = eventType;
    }

    public Event(EventType eventType){
        this(false, eventType);
    }

    public boolean isAsynchronous(){
        return async;
    }

    public EventType getEventType(){
        return eventType;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEventName(){
        return name == null ? this.getClass().getName() : name;

    }

    @Override
    public int compareTo(@NotNull Event o) {
        if(isAsynchronous()) {
            return -1;
        }else {
            return 1;
        }
    }
}

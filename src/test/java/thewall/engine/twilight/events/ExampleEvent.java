package thewall.engine.twilight.events;

import org.jetbrains.annotations.NotNull;

public class ExampleEvent extends Event{
    public ExampleEvent() {
        super(EventType.CUSTOM);
    }

    public static class ListenerEvent implements Listener {
        @EngineEvent(type = EventType.CUSTOM)
        void onEvent(@NotNull ExampleEvent test){
            System.out.println(test.getEventName());
        }

    }

    public static class FakeExampleEvent extends Event{
        public FakeExampleEvent(){
            super(EventType.CUSTOM);
        }
    }

}

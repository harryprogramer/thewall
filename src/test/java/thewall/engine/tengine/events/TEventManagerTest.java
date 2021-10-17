package thewall.engine.tengine.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TEventManagerTest {
    EventManager eventManager = new TEventManager();

    @BeforeEach
    void init(){
        eventManager.registerEvents(new ExampleEvent.ListenerEvent());
    }

    @Test
    void test(){
        eventManager.callEvent(new ExampleEvent());
    }

}
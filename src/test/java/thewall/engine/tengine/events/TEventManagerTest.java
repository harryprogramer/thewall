package thewall.engine.tengine.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thewall.engine.tengine.events.input.gamepad.GamepadConnectedEvent;
import thewall.engine.tengine.events.input.gamepad.GamepadDisconnectedEvent;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class TEventManagerTest {
    EventManager eventManager = new TEventManager();

    @BeforeEach
    void init(){
        eventManager.registerEvents(new ExampleEvent.ListenerEvent());
    }

    void test(){
        eventManager.callEvent(new ExampleEvent());
    }

    @Test
    void test2(){
        Queue<Event> stringQueue = new PriorityQueue<>();

        stringQueue.add(new ExampleEvent());
        stringQueue.add(new GamepadConnectedEvent(null));
        stringQueue.add(new GamepadDisconnectedEvent(null));

        Event first = stringQueue.poll();
        Event second = stringQueue.poll();
        Event third = stringQueue.poll();

        System.out.println(first.getClass().getSimpleName());
        System.out.println(second.getClass().getSimpleName());
        System.out.println(third.getClass().getSimpleName());
    }


}
package thewall.engine.twilight;

import thewall.engine.twilight.audio.SoundMaster;
import thewall.engine.twilight.events.Event;
import thewall.engine.twilight.events.EventManager;
import thewall.engine.twilight.hardware.Hardware;
import thewall.engine.twilight.input.Input;
import thewall.engine.twilight.system.AppSettings;

import javax.swing.text.View;

public interface Application {
    void onInit();

    void update();

    void setInput(Input input);

    void setEventManager(EventManager eventManager);

    EventManager getEventManager();

    Input input();

    SoundMaster getSound();

    void setSound(SoundMaster sound);

    AppSettings getSettings();

    ViewPort getGUIViewPort();

    ViewPort getViewPort();

    Hardware getHardware();
}

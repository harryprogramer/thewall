package thewall.engine.sdk.leveleditor.window;

import thewall.engine.twilight.TwilightApp;

public class TwilightLevelEditor extends TwilightApp implements WindowManager {

    static {
        if(!TwilightApp.isInit()) {
            TwilightApp.init();
        }
    }

    @Override
    public void createWindow(int width, int height) {
        TwilightApp.startApp(this);
    }

    @Override
    public void hideWindow() {
    }

    @Override
    public void showWindow() {

    }

    @Override
    public void setWindowTitle(String title) {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void enginePulse() {

    }

    @Override
    public void closeWindow() {

    }

    @Override
    public void resizeWindow(int width, int height) {

    }

}

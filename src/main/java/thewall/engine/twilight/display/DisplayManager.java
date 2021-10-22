package thewall.engine.twilight.display;

public interface DisplayManager {
    void createDisplay();

    void updateDisplay() throws Exception;


    void enableVSync();

    void disableVSync();
}

package thewall.engine.sdk.leveleditor.window;

public interface WindowManager {
    void createWindow(int width, int height);

    void hideWindow();

    void showWindow();

    void setWindowTitle(String title);

    void closeWindow();
    
    void resizeWindow(int width, int height);
}

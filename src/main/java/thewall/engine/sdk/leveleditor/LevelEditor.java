package thewall.engine.sdk.leveleditor;

import lombok.Getter;
import thewall.engine.sdk.leveleditor.window.awt.LevelEditorJFrame;
import thewall.engine.sdk.leveleditor.window.WindowManager;

public class LevelEditor {
    @Getter
    private static final WindowManager window = new LevelEditorJFrame();

    public static void main(String[] args) {
        window.createWindow(720, 500);
        window.setWindowTitle("Hello World!");
    }
}

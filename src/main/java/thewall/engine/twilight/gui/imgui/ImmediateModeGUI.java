package thewall.engine.twilight.gui.imgui;

public interface ImmediateModeGUI {

    void init();

    void show();

    void hide();

    void destroy();

    void renderBegin();

    void renderEnd();

    ImGuiDesigner getDesigner();
}

package thewall;

import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;

public class ImGuiTest extends Application {
    @Override
    protected void configure(Configuration config) {
        config.setTitle("Dear ImGui is Awesome!");
    }

    @Override
    public void process() {
        ImGui.text("Hello, World!");
    }

    public static void main(String[] args) {
        launch(new ImGuiTest());
    }
}

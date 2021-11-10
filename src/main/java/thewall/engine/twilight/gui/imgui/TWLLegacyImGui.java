package thewall.engine.twilight.gui.imgui;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.app.Application;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import thewall.engine.twilight.display.DisplayManager;
import thewall.engine.twilight.display.GLDisplayManager;
import thewall.engine.twilight.runtime.AbstractRuntime;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class TWLLegacyImGui implements ImmediateModeGUI, ImGuiDesigner {

    private final static Logger logger = LogManager.getLogger(TWLLegacyImGui.class);

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private Thread renderThread = null;

    private boolean isInit = false;


    private final GLDisplayManager displayManager;
    private final String glslVersion;

    boolean showText = false;

    public TWLLegacyImGui(GLDisplayManager displayManager, String glslVersion){
        this.displayManager = displayManager;
        this.glslVersion = glslVersion;
    }


    @Override
    public void init() {
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        imGuiGlfw.init(displayManager.getWindowPointer(), true);
        imGuiGl3.init(glslVersion);
        isInit = true;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void destroy() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
    }


    @Override
    public void renderEnd() {
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = org.lwjgl.glfw.GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    @Override
    public void renderBegin() {
        if(renderThread == null){
            renderThread = Thread.currentThread();
        }

        imGuiGlfw.newFrame();
        ImGui.newFrame();

        /*
        ImGui.begin("Cool Window");

        if (ImGui.button("I am a button")) {
            showText = true;
        }


        if (showText) {
            ImGui.text("You clicked a button");
            ImGui.sameLine();
            if (ImGui.button("Stop showing text")) {
                showText = false;
            }
        }

        ImGui.end();
        */

    }


    @Override
    public ImGuiDesigner getDesigner() {
        return this;
    }

    private void checkThread(){
        if(renderThread != null && Thread.currentThread() != renderThread) {
            throw new IllegalThreadStateException("this function can be use only from render thread");
        }
    }

    @Override
    public void text(String text, String window) {
        checkThread();
        ImGui.begin(window);
        ImGui.text(text);
        ImGui.end();
    }

    @Override
    public boolean button(String text, String window) {
        checkThread();
        boolean x;
        ImGui.begin(window);
        x = ImGui.button(text);
        ImGui.end();
        return x;
    }
}

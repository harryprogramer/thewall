package thewall.engine.twilight.gui.imgui;

import org.jetbrains.annotations.NotNull;
import thewall.engine.twilight.TwilightApp;
import thewall.engine.twilight.events.EngineEvent;
import thewall.engine.twilight.events.EventType;
import thewall.engine.twilight.events.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ImGUIGuard implements ImGuiDesigner {
    private final List<Method> rootMethod;
    private final ImmediateModeGUI gui;

    public ImGUIGuard(TwilightApp app, ImmediateModeGUI gui){
        this.gui = gui;
        this.rootMethod = getMethodsAnnotatedWith(app);
    }

    private static @NotNull List<Method> getMethodsAnnotatedWith(final @NotNull Object type) {
        final List<Method> methods = new ArrayList<>();
        Class<?> klass = type.getClass();
        while (klass != Object.class) {
            for (final Method method : klass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(OnImmediateGUI.class)) {
                    methods.add(method);
                }
            }
            klass = klass.getSuperclass();
        }
        return methods;
    }


    private void checkCallers(){
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(StackTraceElement stackTraceElement : stackTraceElements) {
            for (Method method : rootMethod) {
                if (stackTraceElement.getMethodName().equals(method.getName())){
                    return;
                }
            }
        }

        throw new IllegalStateException("Immediate GUI can be called only from OnImmediateGUI annotation function");
    }

    @Override
    public void text(String text, String window) {
        checkCallers();
        gui.getDesigner().text(text, window);
    }

    @Override
    public boolean button(String text, String window) {
        checkCallers();
        return gui.getDesigner().button(text, window);
    }
}

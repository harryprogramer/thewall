package thewall.game.tengine.display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import thewall.game.Game;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


public class DisplayManager {
    private long window;

    public long getWindow() {
        return window;
    }

    int fps = 0;

    public long getFps() {
        return fps;
    }

    private static double lastFrameTime;
    private static float delta;

    private final int width, height;
    Random r = new Random();

    public float random() {
        r = new Random();
        return .0f + r.nextFloat() * (.9f - .0f);
    }


    public DisplayManager(int width, int height){
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "DisplayManager{" +
                "window=" + window +
                ", fps=" + fps +
                ", width=" + width +
                ", height=" + height +
                ", r=" + r +
                '}';
    }

    public void createDisplay(){
        window = GLFW.glfwCreateWindow(width, height, "App",  0, 0);
        glfwMakeContextCurrent(window);

        glfwShowWindow(window);

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }

            if(key == GLFW_KEY_F3 && action == GLFW_RELEASE){
                if(glfwGetInputMode(window, GLFW_CURSOR) == GLFW_CURSOR_NORMAL){
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                }else {
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                }
            }
        });


        /*
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            assert vidmode != null;
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

         */

        GL.createCapabilities();

        GL11.glEnable(GL_DEPTH_TEST);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int argWidth, int argHeight) {
                Game.getMasterRenderer().resizeWindow(argWidth, argHeight);
            }
        });
        lastFrameTime = getCurrentTime();
    }



    public void updateDisplay() throws Exception {
        //glClearColor(random(), random(), random(), 0.0f);
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwSwapBuffers(window);

        glfwPollEvents();

        double currentFrameTime = getCurrentTime();
        delta = (float) ((currentFrameTime - lastFrameTime) / 1000f);
        lastFrameTime = currentFrameTime;
    }

    private static long getCurrentTime(){
        return System.currentTimeMillis() * 1000 / 1000;
    }

    public static float getFrameTimeSeconds(){
        return delta;
    }

    public void enableVSync(){
        glfwSwapInterval(1);
    }

    public void disableVSync(){
        glfwSwapInterval(0);
    }

}

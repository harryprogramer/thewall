package thewall.engine.tengine.input.mouse;

import org.lwjgl.glfw.GLFW;

public class MouseMovement {
    private final long window;
    private int dwheel, dy, dx;

    private static class MouseWheelImpl implements MouseWheel, Cloneable {
        private final int dwheel, dy, dx;

        public MouseWheelImpl(int dwheel, int dy, int dx){
            this.dwheel = dwheel;
            this.dy = dy;
            this.dx = dx;
        }

        @Override
        public int getDWheel() {
            return dwheel;
        }

        @Override
        public int getDX() {
            return dy;
        }

        @Override
        public int getDY() {
            return dx;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public MouseMovement(long window){
        if(window == 0){
            throw new NullPointerException("Null pointer");
        }
        this.window = window;
    }

    private void createCallback(){
        GLFW.glfwSetScrollCallback(window, (window1, xoffset, yoffset) -> {

        });
    }

    public void getMouseMovement(){

    }

}

package thewall.game.tengine.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jetbrains.annotations.NotNull;
import thewall.game.tengine.entity.Camera;

public class Maths {
    static int index = 0;
    private static double[] cos = new double[361];
    private static double[] sin = new double[361];
    private static boolean isCosSineTableIndexed = false;


    public static void generateIndexedCosSineTable(){
        for (int i = 0; i <= 360; i++) {
            cos[i] = Math.cos(Math.toRadians(i));
            sin[i] = Math.sin(Math.toRadians(i));
        }
    }

    public static double getSine(int angle) {
        int angleCircle = angle % 360;
        return sin[angleCircle];
    }

    public static double getCos(int angle) {
        int angleCircle = angle % 360;
        return cos[angleCircle];
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry,
                                                      float rz, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(translation);
        matrix.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0));
        matrix.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0));
        matrix.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1));
        matrix.scale(new Vector3f(scale,scale,scale));
        return matrix;
    }

    public static @NotNull Matrix4f createViewMatrix(@NotNull Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        viewMatrix.translate(negativeCameraPos);
        return viewMatrix;
    }


}

package thewall.game.engine.shaders;

import thewall.game.engine.entity.Camera;
import thewall.game.engine.entity.Light;
import thewall.game.engine.utils.Maths;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightColor;
    private int locationLightPosition;
    private int locationReflectivity;
    private int locationShaneDamper;
    private int location_useFakeLighting;

    public StaticShader(){
        super("vertexShader.frag", "fragmentShader.vert");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationLightColor = super.getUniformLocation("lightColour");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationReflectivity = super.getUniformLocation("reflectivity");
        locationShaneDamper = super.getUniformLocation("shineDamper");
        location_useFakeLighting = super.getUniformLocation("useFakeLighting");
    }

    public void loadTransformationMatrix(Matrix4f matrix4f){
        super.loadMatrix(locationTransformationMatrix, matrix4f);
    }

    public void loadShineVariables(float dumper, float reflectivity){
        super.loadFloat(locationReflectivity, reflectivity);
        super.loadFloat(locationShaneDamper, dumper);
    }

    public void loadProjectionMatrix(Matrix4f matrix4f){
        super.loadMatrix(locationProjectionMatrix, matrix4f);
    }

    public void loadViewMatrix(Camera camera){
        super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
    }

    public void loadFakeLighting(boolean useFake){
        super.loadBoolean(location_useFakeLighting, useFake);
    }

    public void loadLight(@NotNull Light light){
        super.loadVector(locationLightColor, light.getColour());
        super.loadVector(locationLightPosition, light.getPosition());
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoord");
        super.bindAttribute(2, "normal");
    }
}

package thewall.engine.twilight.shaders;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import thewall.engine.twilight.entity.Camera;
import thewall.engine.twilight.entity.Light;
import thewall.engine.twilight.utils.Maths;

public class TerrainShader extends ShaderProgram{
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightColor;
    private int locationLightPosition;
    private int locationReflectivity;
    private int locationShaneDamper;
    private int locationSkyColor;
    private int locationBackgroundTexture;
    private int locationRTexture;
    private int locationGTexture;
    private int locationBTexture;
    private int locationBlendMap;

    public TerrainShader(){
        super("terrainVertexShader.vert", "terrainfragmentShader.frag");
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
        locationSkyColor = super.getUniformLocation("skyColor");
        locationBackgroundTexture = super.getUniformLocation("backgroundTexture");
        locationRTexture = super.getUniformLocation("rTexture");
        locationGTexture = super.getUniformLocation("gTexture");
        locationBTexture = super.getUniformLocation("bTexture");
        locationBlendMap = super.getUniformLocation("blendMap");
    }

    public void connectTextureUnits(){
        super.loadInt(locationBackgroundTexture, 0);
        super.loadInt(locationRTexture, 1);
        super.loadInt(locationGTexture, 2);
        super.loadInt(locationBTexture, 3);
        super.loadInt(locationBlendMap, 4);
    }

    public void loadSkyColor(float r, float g, float b){
        super.loadVector(locationSkyColor, new Vector3f(r, g, b));
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

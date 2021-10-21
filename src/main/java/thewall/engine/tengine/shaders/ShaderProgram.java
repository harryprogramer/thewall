package thewall.engine.tengine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import thewall.engine.tengine.utils.ResourceUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public abstract class ShaderProgram {
    private static final Logger logger = LogManager.getLogger(ShaderProgram.class);

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile){
        this.vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        this.programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        System.out.println(GL20.glGetProgramInfoLog(programID));
        getAllUniformLocations();
    }

    public void start(){
        GL20.glUseProgram(programID);
    }

    public void stop(){
        GL20.glUseProgram(0);
    }

    public void cleanUp(){
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    protected int getUniformLocation(String uniformName){
        int location = GL20.glGetUniformLocation(programID, uniformName);
        if(location == -1){
            logger.error("Uniform [" + uniformName + "] not found");
        }

        return location;
    }

    protected void loadFloat(int location, float value){
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, @NotNull Vector3f vector3f){
        GL20.glUniform3f(location, vector3f.x, vector3f.y, vector3f.z);
    }

    protected void loadInt(int location , @NotNull int value){
        GL20.glUniform1i(location, value);
    }

    protected void loadBoolean(int location, boolean value){
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, @NotNull Matrix4f matrix4f){
        matrix4f.get(matrixBuffer);
        //matrixBuffer.flip();
        GL20.glUniformMatrix4fv(location, false, matrixBuffer);
    }


    protected abstract void getAllUniformLocations();

    @SneakyThrows
    private static int loadShader(String file, int type){
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, ResourceUtils.readFromInputStream(ShaderProgram.class.getResourceAsStream("/shaders/" + file)));
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            logger.error("GLSL Shader compile error from: [" + file + "]");
            logger.error("Shader error details:\n" + GL20.glGetShaderInfoLog(shaderID));
        }
        return shaderID;
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
}

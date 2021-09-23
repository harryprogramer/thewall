package thewall.game.engine.textures;

import lombok.Getter;
import lombok.Setter;

public class ModelTexture {
    private final int textureID;

    private float shineDamper = 1;
    private float reflectivity = 0;
    @Setter
    @Getter
    private boolean hasTransparency = false;

    @Getter
    @Setter
    private boolean useFakeLighting = false;

    public ModelTexture(int texture){
        this.textureID = texture;
    }

    public int getID(){
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

}

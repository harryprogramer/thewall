package thewall.engine.twilight.models;

import lombok.Getter;

@Deprecated
public class RawModel {
    @Getter
    private final int vaoID;
    @Getter
    private final int vertexCount;

    public RawModel(int vaoID, int vertexCount){
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }
}

package thewall.engine.tengine.models;

import lombok.Getter;

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

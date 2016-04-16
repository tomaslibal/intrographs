package eu.libal.intrographs.presentation.shapes;

import java.io.Serializable;

public class VertexLabelShape2D extends TextShape2D implements Serializable {
    private final VertexShape2D<?> vertexShape;

    public VertexLabelShape2D(VertexShape2D<?> vertexShape) {
        this.vertexShape = vertexShape;
        subscribe();
    }

    private void subscribe() {
        vertexShape.listenableCoords.subscribe("update", this::updateCoords);
    }

    private void updateCoords(String msg) {
        setX(vertexShape.listenableCoords.getValue().getX() - 5);
        setY(vertexShape.listenableCoords.getValue().getY() + 20);
    }
}

package eu.libal.intrographs.presentation.shapes;

import java.io.Serializable;

public class VertexLabelShape2D extends TextShape2D implements Serializable {
    private final VertexShape2D<?> vertexShape;

    public static final double X_OFFSET = -5.0;
    public static final double Y_OFFSET = 20.0;

    public VertexLabelShape2D(VertexShape2D<?> vertexShape) {
        this.vertexShape = vertexShape;
        updateCoords("init");
        subscribe();
    }

    private void subscribe() {
        vertexShape.listenableCoords.subscribe("update", this::updateCoords);
    }

    private void updateCoords(String msg) {
        setX(vertexShape.listenableCoords.getValue().getX() + X_OFFSET);
        setY(vertexShape.listenableCoords.getValue().getY() + Y_OFFSET);
    }
}

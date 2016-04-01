package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

/**
 *
 */
public class EdgeShape2D extends BasicShape2D {

    private VertexShape2D sourceId;
    private VertexShape2D targetId;

    public EdgeShape2D(VertexShape2D source, VertexShape2D target) {
        // x, y - unused, position of the edge segment is determined by coordinates of the source and target vertices
        // width - width of the line
        // height - unused
        super(0, 0, 2, 0);

        sourceId = source;
        targetId = target;
    }

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        if (sourceId == null || targetId == null) {
            return;
        }

        ctx.beginPath();
        ctx.setStroke(new Color(0, 0, 0, 1));
        ctx.moveTo(sourceId.getX(), sourceId.getY());
        ctx.lineTo(targetId.getX(), targetId.getY());
        ctx.stroke();
        ctx.closePath();
    }

    public VertexShape2D getSourceId() {
        return sourceId;
    }

    public VertexShape2D getTargetId() {
        return targetId;
    }

    public static class EdgeShapeBuilder {
        public static EdgeShape2D buildAndCreate(VertexShape2D source, VertexShape2D target) {
            return new EdgeShape2D(source, target);
        }
    }
}

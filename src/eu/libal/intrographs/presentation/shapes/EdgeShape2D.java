package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 *
 */
public class EdgeShape2D extends BasicShape2D implements Serializable {

    private VertexShape2D sourceVertexShape2D;
    private VertexShape2D targetVertexShape2D;

    private boolean isHighlighted = false;

    public EdgeShape2D(VertexShape2D source, VertexShape2D target) {
        // x, y - unused, position of the edge segment is determined by coordinates of the source and target vertices
        // width - width of the line
        // height - unused
        super(0, 0, 2, 0);

        sourceVertexShape2D = source;
        targetVertexShape2D = target;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }

    public boolean toggleHighlight() {
        return isHighlighted = !isHighlighted;
    }

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        if (sourceVertexShape2D == null || targetVertexShape2D == null) {
            return;
        }

        ctx.beginPath();
        ctx.setStroke(new Color(0, 0, 0, 1));
        ctx.setLineWidth(1);
        ctx.moveTo(sourceVertexShape2D.getX(), sourceVertexShape2D.getY());
        ctx.lineTo(targetVertexShape2D.getX(), targetVertexShape2D.getY());
        ctx.stroke();
        ctx.closePath();

        if (isHighlighted) {
            ctx.beginPath();
            ctx.setStroke(new Color(0.9, 0, 0, 0.5));
            ctx.setLineWidth(3);
            ctx.moveTo(sourceVertexShape2D.getX(), sourceVertexShape2D.getY());
            ctx.lineTo(targetVertexShape2D.getX(), targetVertexShape2D.getY());
            ctx.stroke();
            ctx.closePath();
        }
    }

    public VertexShape2D getSourceVertexShape2D() {
        return sourceVertexShape2D;
    }

    public VertexShape2D getTargetVertexShape2D() {
        return targetVertexShape2D;
    }

    public static class EdgeShapeBuilder {
        public static EdgeShape2D buildAndCreate(VertexShape2D source, VertexShape2D target) {
            return new EdgeShape2D(source, target);
        }
    }
}

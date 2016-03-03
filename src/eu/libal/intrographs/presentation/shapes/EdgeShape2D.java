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

        Coordinates2D source = sourceId.getCoords();
        Coordinates2D target = targetId.getCoords();

        ctx.beginPath();
        ctx.setStroke(new Color(0, 0, 0, 1));
        ctx.moveTo(source.getX(), source.getY());
        ctx.lineTo(target.getX(), target.getY());
        ctx.stroke();
        ctx.closePath();
    }
}

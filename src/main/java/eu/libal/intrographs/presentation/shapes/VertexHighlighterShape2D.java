package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

/**
 *
 */
public class VertexHighlighterShape2D extends BasicShape2D {

    public VertexHighlighterShape2D() {

    }

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        ctx.setStroke(new Color(0, 0, 0, 1));

        ctx.beginPath();
        ctx.rect(getX(), getY(), getWidth(), getHeight());
        ctx.stroke();
        ctx.closePath();
    }
}

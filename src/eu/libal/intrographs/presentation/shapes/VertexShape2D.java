package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

/**
 *
 */
public class VertexShape2D extends BasicShape2D {

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        ctx.setFill(new Color(0, 0, 0.99, 1));
        ctx.fillOval(getX(), getY(),  getWidth(), getHeight());
    }

}

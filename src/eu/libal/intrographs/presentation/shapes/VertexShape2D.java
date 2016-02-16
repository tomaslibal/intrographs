package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

/**
 *
 */
public class VertexShape2D extends BasicShape2D {

    private String vertexId;

    public VertexShape2D(int x, int y, String vertexId) {
        super(x, y, 10, 10);
        this.vertexId = vertexId;
    }

    public String getVertexId() {
        return vertexId;
    }

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        ctx.setFill(new Color(0, 0, 0.99, 1));

        Integer width = getWidth();
        Integer height = getHeight();

        /*
         * center the circle to the (x, y) position
         */
        ctx.fillOval(getX() - (width/2), getY() - (height/2), width, height);
    }

}

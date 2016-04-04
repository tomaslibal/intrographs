package eu.libal.intrographs.presentation.shapes;

import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.scene.paint.Color;

/**
 *
 */
public class VertexShape2D<T> extends BasicShape2D {

    private final Vertex<T> vertex;
    private final Coordinates2D displacement = new Coordinates2D();

    public VertexShape2D(int x, int y, Vertex<T> vertex) {
        super(x, y, 10, 10);
        this.vertex = vertex;
    }

    public String getVertexId() {
        return vertex.getId();
    }

    public Vertex<T> getVertex() {
        return vertex;
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

    @Override
    public Integer getX() {
        return coords.getX() + displacement.getX();
    }

    @Override
    public Integer getY() {
        return coords.getY() + displacement.getY();
    }

    public Coordinates2D getDisplacement() {
        return displacement;
    }

    public void setDisplacementX(int x) {
        displacement.setX(x);
    }

    public void setDisplacementY(int y) {
        displacement.setY(y);
    }

    public static class VertexShapeBuilder<T> {
        private int x = 0;
        private int y = 0;
        private Vertex<T> v;

        public VertexShapeBuilder withVertex(Vertex<T> v) {
            this.v = v;
            return this;
        }

        public VertexShapeBuilder withXCoordinate(int x) {
            this.x = x;
            return this;
        }

        public VertexShapeBuilder withYCoordinate(int y) {
            this.y = y;
            return this;
        }

        public VertexShape2D<T> create() {
            return new VertexShape2D<>(x, y, v);
        }

        public static <T> VertexShape2D<T> buildAndCreate(Vertex<T> v, int x, int y) {
            return new VertexShape2D<>(x, y, v);
        }
    }
}

package eu.libal.intrographs.presentation.shapes;

import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.scene.paint.Color;

/**
 *
 */
public class VertexShape2D extends BasicShape2D {

    private String vertexId;
    private final IVertex<Integer> vertex;
    private final Coordinates2D displacement = new Coordinates2D();

    public VertexShape2D(int x, int y, IVertex<Integer> vertex) {
        super(x, y, 10, 10);
        this.vertexId = vertex.getId();
        this.vertex = vertex;
    }

    public String getVertexId() {
        return vertexId;
    }

    public IVertex<Integer> getVertex() {
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

    public static class VertexShapeBuilder {
        private int x = 0;
        private int y = 0;
        private IVertex v;

        public VertexShapeBuilder withVertex(IVertex v) {
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

        public VertexShape2D create() {
            return new VertexShape2D(x, y, v);
        }

        public static <VertexType> VertexShape2D buildAndCreate(IVertex<VertexType> v, int x, int y) {
            return new VertexShape2D(x, y, (IVertex<Integer>) v);
        }
    }
}

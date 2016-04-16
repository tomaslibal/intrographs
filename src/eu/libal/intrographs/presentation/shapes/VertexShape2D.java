package eu.libal.intrographs.presentation.shapes;

import eu.libal.intrographs.common.ListenableField;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;


/**
 *
 */
public class VertexShape2D<T> extends BasicShape2D implements Serializable {

    private static final long serialVersionUID = 4412345678L;

    public ListenableField<Coordinates2D> listenableCoords = new ListenableField<>();

    private Vertex<T> vertex;
    private Coordinates2D displacement = new Coordinates2D();

    private boolean isHighlighted = false;
    private Color vertexColor;

    public VertexShape2D(int x, int y, Vertex<T> vertex) {
        super(x, y, 10, 10);
        listenableCoords.setValue(coords);
        this.vertex = vertex;
        vertexColor = new Color(0, 0, 0.99, 1);
    }

    public Color getVertexColor() {
        return vertexColor;
    }

    public void setVertexColor(Color vertexColor) {
        this.vertexColor = vertexColor;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
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

        ctx.setFill(vertexColor);

        Integer width = getWidth();
        Integer height = getHeight();

        /*
         * center the circle to the (x, y) position
         */
        ctx.beginPath();
        ctx.fillOval(getX() - (width/2), getY() - (height/2), width, height);
        ctx.closePath();

        /*
         * the highlight effect
         */
        if (isHighlighted) {
            ctx.setStroke(new Color(0.15, 0.15, 0.15, 1));
            ctx.beginPath();
            ctx.rect(getX() - width, getY() - height, width*2, height*2);
            ctx.stroke();
            ctx.closePath();
        }
    }

    @Override
    public Double getX() {
        return coords.getX();
    }

    @Override
    public Double getY() {
        return coords.getY();
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        listenableCoords.setValue(coords);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        listenableCoords.setValue(coords);
    }

    public Coordinates2D getDisplacement() {
        return displacement;
    }

    public void setDisplacementX(int x) {
        displacement.setX(x);
    }

    public void addDisplacementX(double x) {
        displacement.setX(displacement.getX() + x);
    }

    public void addDisplacementY(double y) {
        displacement.setY(displacement.getY() + y);
    }

    public void multDisplacementX(double x) {
        displacement.setX(displacement.getX() * x);
    }

    public void multDisplacementY(double y) {
        displacement.setY(displacement.getY() * y);
    }

    public void setDisplacementY(int y) {
        displacement.setY(y);
    }

    public static Color getDefaultColor() {
        return new Color(0, 0, 1, 1);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(listenableCoords);
        out.writeObject(vertex);
        out.writeObject(isHighlighted);
        out.writeObject(vertexColor.toString());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        listenableCoords = (ListenableField<Coordinates2D>) in.readObject();
        vertex = (Vertex<T>) in.readObject();
        isHighlighted = (boolean) in.readObject();
        vertexColor = Color.valueOf((String) in.readObject());
    }

    private void readObjectNoData() throws ObjectStreamException {

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

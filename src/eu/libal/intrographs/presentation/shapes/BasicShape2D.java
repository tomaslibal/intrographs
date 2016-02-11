package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public abstract class BasicShape2D extends BasicRenderableShape implements IShape2D {

    private Coordinates2D coords = new Coordinates2D();
    private Dimensions2D dims = new Dimensions2D();

    public BasicShape2D() {
    }

    public BasicShape2D(int x, int y) {
        coords.set(x, y);
    }

    public BasicShape2D(int x, int y, int width, int height) {
        coords.set(x, y);
        dims.set(width, height);
    }

    @Override
    public Integer getX() {
        return coords.getX();
    }

    @Override
    public Integer getY() {
        return coords.getY();
    }

    @Override
    public Coordinates2D getCoords() {
        return coords;
    }

    @Override
    public Integer getWidth() {
        return dims.getWidth();
    }

    @Override
    public Integer getHeight() {
        return dims.getHeight();
    }

    @Override
    public Dimensions2D getDims() {
        return dims;
    }
}
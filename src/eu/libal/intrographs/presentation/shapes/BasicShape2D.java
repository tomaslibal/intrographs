package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public abstract class BasicShape2D implements IShape2D {

    private Coordinates2D coords = new Coordinates2D();

    public BasicShape2D() {
    }

    public BasicShape2D(int x, int y) {
        coords.set(x, y);
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
}

package eu.libal.intrographs.presentation.shapes;

import java.util.Vector;

/**
 *
 */
public class Coordinates2D<T> {
    private Vector<Double> coordsXY = new Vector<>(2);

    public Coordinates2D() {
        coordsXY.add(0d);
        coordsXY.add(0d);
    }

    public Coordinates2D(int x, int y) {
        coordsXY.add((double) x);
        coordsXY.add((double) y);
    }

    public Coordinates2D(double x, double y) {
        coordsXY.add(x);
        coordsXY.add(y);
    }

    public Vector<Double> getCoords() {
        return coordsXY;
    }

    public Double getX() {
        return coordsXY.get(0);
    }

    public Double getY() {
        return coordsXY.get(1);
    }

    public void setX(double x) {
        coordsXY.setElementAt(x, 0);
    }

    public void setY(double y) {
        coordsXY.setElementAt(y, 1);
    }

    public void set(double x, double y) {
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        coordsXY.setElementAt((double) x, 0);
    }

    public void setY(int y) {
        coordsXY.setElementAt((double) y, 1);
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }
}

package eu.libal.intrographs.presentation.shapes;

import java.util.Vector;

/**
 *
 */
public class Coordinates2D {
    Vector<Integer> coordsXY = new Vector<>(2);

    public Coordinates2D() {
        coordsXY.add(0);
        coordsXY.add(0);
    }

    public Coordinates2D(int x, int y) {
        coordsXY.add(x);
        coordsXY.add(y);
    }

    public Coordinates2D(double x, double y) {
        coordsXY.add((int) Math.round(x));
        coordsXY.add((int) Math.round(y));
    }

    public Vector<Integer> getCoords() {
        return coordsXY;
    }

    public Integer getX() {
        return coordsXY.get(0);
    }

    public Integer getY() {
        return coordsXY.get(1);
    }

    public void setX(int x) {
        coordsXY.setElementAt(x, 0);
    }

    public void setY(int y) {
        coordsXY.setElementAt(y, 1);
    }

    public void set(int x, int y) {
        setX(x);
        setY(y);
    }
}

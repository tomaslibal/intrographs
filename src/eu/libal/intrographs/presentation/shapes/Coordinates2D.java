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

    Vector<Integer> getCoords() {
        return coordsXY;
    }

    Integer getX() {
        return coordsXY.get(0);
    }

    Integer getY() {
        return coordsXY.get(1);
    }

    void setX(int x) {
        coordsXY.setElementAt(x, 0);
    }

    void setY(int y) {
        coordsXY.setElementAt(y, 1);
    }

    void set(int x, int y) {
        setX(x);
        setY(y);
    }
}

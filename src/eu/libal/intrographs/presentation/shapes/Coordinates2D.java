package eu.libal.intrographs.presentation.shapes;

import java.util.Vector;

/**
 *
 */
public class Coordinates2D {
    Vector<Integer> coordsXY = new Vector<>(0, 0);

    public Coordinates2D() {

    }

    public Coordinates2D(int x, int y) {
        coordsXY = new Vector<>(x, y);
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
}

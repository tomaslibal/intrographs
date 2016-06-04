package eu.libal.intrographs.presentation.shapes;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 */
public class Coordinates2D implements Serializable {
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

    /**
     * Adds a scalar to each element of the coordiantes
     * @param s coordinates
     * @param scalar the scalar value to be added to x, y of the coordinates
     * @return new coordinates
     */
    public static Coordinates2D add(Coordinates2D s, double scalar) {
        return new Coordinates2D(s.getX() + scalar, s.getY() + scalar);
    }

    /**
     * Adds two coordinates together
     * @param s first coordinates
     * @param t second coordinates
     * @return new coordinates
     */
    public static Coordinates2D add(Coordinates2D s, Coordinates2D t) {
        return new Coordinates2D(s.getX() + t.getX(), s.getY() + t.getY());
    }

    /**
     * Subtracts one coordinates from another coordinates
     * @param s the coordinates being subtracted from
     * @param t the coordinates whose values will be subtracted from the first vector
     * @return new coordinates
     */
    public static Coordinates2D sub(Coordinates2D s, Coordinates2D t) {
        return new Coordinates2D(s.getX() - t.getX(), s.getY() - t.getY());
    }

    /**
     * Multiplies the coordinates by a scalar value
     * @param s coordinates vector
     * @param scalar scalar value
     * @return new coordinates
     */
    public static Coordinates2D mult(Coordinates2D s, double scalar) {
        return new Coordinates2D(s.getX() * scalar, s.getY() * scalar);
    }

    /**
     * Returns the dot product between two coordinates (2d vectors)
     *
     * @param s first coordinate (2d vector)
     * @param t second coordinate (2d vector)
     * @return the dot product between s and t (a scalar value)
     */
    public static double dot(Coordinates2D s, Coordinates2D t) {
        return s.getX() * t.getX() + s.getY() * t.getY();
    }
}

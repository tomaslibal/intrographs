package eu.libal.intrographs.presentation.shapes;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 */
public class Dimensions2D implements Serializable {
    private Vector<Integer> dims = new Vector<>(2);

    public Dimensions2D() {
        dims.add(0);
        dims.add(0);
    }

    public Dimensions2D(int width, int height) {
        dims.add(width);
        dims.add(height);
    }

    public void set(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int width) {
        dims.setElementAt(width, 0);
    }

    public void setHeight(int height) {
        dims.setElementAt(height, 1);
    }

    public Integer getWidth() {
        return dims.elementAt(0);
    }

    public Integer getHeight() {
        return dims.elementAt(1);
    }
}

package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public interface IShape2D {
    Double getX();
    Double getY();
    Coordinates2D getCoords();
    void setX(int x);
    void setY(int y);
    void setX(double x);
    void setY(double y);

    Integer getWidth();
    Integer getHeight();
    void setWidth(int w);
    void setHeight(int h);
    Dimensions2D getDims();
}

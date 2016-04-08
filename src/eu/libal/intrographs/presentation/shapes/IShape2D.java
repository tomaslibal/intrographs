package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public interface IShape2D {
    Double getX();
    Double getY();
    Coordinates2D getCoords();
    @Deprecated
    void setX(int x);
    @Deprecated
    void setY(int y);
    void setX(double x);
    void setY(double y);

    Integer getWidth();
    Integer getHeight();
    void setWidth(int w);
    void setHeight(int h);
    Dimensions2D getDims();
}

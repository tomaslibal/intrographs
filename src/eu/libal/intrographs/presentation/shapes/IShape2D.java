package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public interface IShape2D {
    Integer getX();
    Integer getY();
    Coordinates2D getCoords();
    void setX(int x);
    void setY(int y);

    Integer getWidth();
    Integer getHeight();
    Dimensions2D getDims();
}

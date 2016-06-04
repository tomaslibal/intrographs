package eu.libal.intrographs.presentation.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public interface IRenderableShape {
    void setContext(GraphicsContext ctx);
    void paint() throws Exception;
}

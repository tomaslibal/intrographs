package eu.libal.intrographs.presentation.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 */
public abstract class BasicShape implements IRenderableShape {
    GraphicsContext ctx;

    @Override
    public void setContext(GraphicsContext ctx) {
        this.ctx = ctx;
    }
}

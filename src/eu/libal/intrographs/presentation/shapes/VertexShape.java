package eu.libal.intrographs.presentation.shapes;

/**
 *
 */
public class VertexShape extends BasicRenderableShape {

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }
    }
}

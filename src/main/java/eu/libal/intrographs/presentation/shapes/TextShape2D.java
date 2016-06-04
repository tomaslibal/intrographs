package eu.libal.intrographs.presentation.shapes;

import javafx.scene.paint.Color;

/**
 *
 */
public class TextShape2D extends BasicShape2D {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void paint() throws Exception {
        if (ctx == null) {
            throw new Exception("No GraphicsContext");
        }

        ctx.setFill(new Color(0,0,0,1));
        ctx.fillText(text, coords.getX(), coords.getY());
    }
}

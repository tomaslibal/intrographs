package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;

import java.util.Set;

/**
 *
 */
public class RandomGraphLayout implements IGraphLayout2D {

    @Override
    public void run(Set<VertexShape2D> vertexShapes, Set<EdgeShape2D> edgeShapes) {
        vertexShapes.stream()
                .forEach(shape -> {
                    shape.setX((int) Math.round(Math.random()*100));
                    shape.setY((int) Math.round(Math.random()*100));
                });
    }
}

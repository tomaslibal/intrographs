package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;

import java.util.Set;

/**
 *
 */
public interface IGraphLayout2D {
    void run(Set<VertexShape2D> vertexShapes, Set<EdgeShape2D> edgeShapes);
}

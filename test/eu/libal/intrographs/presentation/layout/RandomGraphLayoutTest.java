package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 */
public class RandomGraphLayoutTest {

    private RandomGraphLayout randomLayout;

    @Before
    public void setup() {
        randomLayout = new RandomGraphLayout();
    }

    @Test
    public void shouldUpdateRandomlyXYCoordinatesOfEachVertex() throws Exception {
        Set<VertexShape2D> vertices = new HashSet<VertexShape2D>();
        Set<EdgeShape2D> edges = new HashSet<EdgeShape2D>();

        VertexShape2D a = mock(VertexShape2D.class);
        vertices.add(a);
        VertexShape2D b = mock(VertexShape2D.class);
        vertices.add(b);

        randomLayout.run(vertices, edges);

        verify(a).setX(anyInt());
        verify(a).setY(anyInt());
        verify(b).setX(anyInt());
        verify(b).setY(anyInt());

    }
}
package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.scene.canvas.Canvas;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 *
 */
public class RandomGraphLayoutTest {

    private RandomGraphLayout randomLayout;

    @Mock
    GraphRenderer<Integer, Edge<Integer>> mockGraphRenderer;

    @Mock
    MessageBus mockMessageBus;

    @Mock
    Semaphore mockCanUpdateLayout;

    Canvas canvas;

    @Before
    public void setup() {
        mockGraphRenderer = Mockito.mock(GraphRenderer.class);
        mockCanUpdateLayout = Mockito.mock(Semaphore.class);
        mockMessageBus = Mockito.mock(MessageBus.class);
        canvas = new Canvas();

        when(mockGraphRenderer.getCanvas()).thenReturn(canvas);

        randomLayout = new RandomGraphLayout(mockGraphRenderer, mockMessageBus, mockCanUpdateLayout);
    }

    @Test
    public void shouldUpdateRandomlyXYCoordinatesOfEachVertex() throws Exception {
        Set<VertexShape2D<Integer>> vertices = new HashSet<>();
        Set<EdgeShape2D> edges = new HashSet<EdgeShape2D>();

        VertexShape2D a = mock(VertexShape2D.class);
        vertices.add(a);
        VertexShape2D b = mock(VertexShape2D.class);
        vertices.add(b);

        when(mockGraphRenderer.getVertexShapes()).thenReturn(vertices);

        randomLayout.run();

        verify(a, times(RandomGraphLayout.NUM_STEPS)).setX(anyInt());
        verify(a, times(RandomGraphLayout.NUM_STEPS)).setY(anyInt());
        verify(b, times(RandomGraphLayout.NUM_STEPS)).setX(anyInt());
        verify(b, times(RandomGraphLayout.NUM_STEPS)).setY(anyInt());

    }
}
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
import java.util.concurrent.Semaphore;

import static org.mockito.Matchers.anyFloat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 *
 */
public class RandomGraphLayoutTest {

    private RandomGraphLayout randomLayout;

    @Mock
    GraphRenderer<Integer, Edge> mockGraphRenderer;

    @Mock
    MessageBus mockMessageBus;

    @Mock
    Semaphore mockCanUpdateLayout;

    Canvas canvas;

    private Double runLength = 5d;

    @Before
    public void setup() {
        mockGraphRenderer = Mockito.mock(GraphRenderer.class);
        mockCanUpdateLayout = Mockito.mock(Semaphore.class);
        mockMessageBus = Mockito.mock(MessageBus.class);
        canvas = new Canvas();

        when(mockGraphRenderer.getCanvas()).thenReturn(canvas);

        randomLayout = new RandomGraphLayout(mockGraphRenderer, mockMessageBus, mockCanUpdateLayout, runLength);
    }

    @Test
    public void shouldUpdateRandomlyXYCoordinatesOfEachVertex() throws Exception {
        HashSet<VertexShape2D<Integer>> vertices = new HashSet<>();
        HashSet<EdgeShape2D> edges = new HashSet<EdgeShape2D>();

        VertexShape2D a = mock(VertexShape2D.class);
        vertices.add(a);
        VertexShape2D b = mock(VertexShape2D.class);
        vertices.add(b);

        when(mockGraphRenderer.getVertexShapes()).thenReturn(vertices);

        randomLayout.run();

        int NUM_STEPS = (int) Math.round(runLength * (1000 / RandomGraphLayout.MILLIS));

        verify(a, times(NUM_STEPS)).setX(anyFloat());
        verify(a, times(NUM_STEPS)).setY(anyFloat());
        verify(b, times(NUM_STEPS)).setX(anyFloat());
        verify(b, times(NUM_STEPS)).setY(anyFloat());

    }
}
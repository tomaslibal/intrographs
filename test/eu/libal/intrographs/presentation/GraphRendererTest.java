package eu.libal.intrographs.presentation;

import eu.libal.intrographs.JavaFXThreadingRule;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import javafx.scene.canvas.Canvas;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

public class GraphRendererTest {

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    private GraphRenderer<Integer, Edge<Integer>> graphRenderer;
    private Graph<Integer, Edge<Integer>> g;
    private Canvas canvas;

    @Before
    public void setup() {
        canvas = Mockito.mock(Canvas.class);
        g = Mockito.mock(Graph.class);
        graphRenderer = new GraphRenderer<>(g, canvas);
    }

    @Test
    public void shouldSubscribeToVertexAndEdgeAddAndRemoveEvents() {
        verify(g).subscribe(eq("graph.vertex.add"), any());
        verify(g).subscribe(eq("graph.vertex.remove"), any());
        verify(g).subscribe(eq("graph.edge.add"), any());
    }

    @Test
    public void shouldReRenderTheCanvasWhenEnablingLabels() {
        GraphRenderer<Integer, Edge<Integer>> spy = Mockito.spy(graphRenderer);

        Canvas realCanvas = new Canvas();
        spy.setCanvas(realCanvas);

        spy.displayLabels();

        verify(spy).render();
    }

    @Test
    public void shouldReRenderTheCanvasWhenDisablingLabels() {
        GraphRenderer<Integer, Edge<Integer>> spy = Mockito.spy(graphRenderer);

        Canvas realCanvas = new Canvas();
        spy.setCanvas(realCanvas);

        spy.hideLabels();

        verify(spy).render();
    }

    @Test
    public void shouldClearCanvasEveryTimeRenderInvokes() {
        GraphRenderer<Integer, Edge<Integer>> spy = Mockito.spy(graphRenderer);

        Canvas realCanvas = new Canvas();
        spy.setCanvas(realCanvas);

        spy.render();

        verify(spy).clearCanvas();
    }

}
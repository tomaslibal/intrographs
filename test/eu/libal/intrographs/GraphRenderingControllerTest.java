package eu.libal.intrographs;

import eu.libal.intrographs.common.INotifiable;
import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.controllers.GraphRenderingController;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GraphRenderingControllerTest {

    GraphRenderingController controller;

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    @Mock
    private ContextMenu mockContextMenu;

    private GraphicsContext ctx;

    @Mock
    private GraphRenderer mockGraphRenderer;

    @Mock
    MessageBus mockMessageBus = Mockito.mock(MessageBus.class);

    @Before
    public void setup() {
        mockContextMenu = Mockito.mock(ContextMenu.class);

        Canvas canvas = new Canvas();
        mockGraphRenderer = Mockito.mock(GraphRenderer.class);

        controller = new GraphRenderingController();
        controller.setCanvas(canvas);
        controller.setMessageBus(mockMessageBus);
        controller.setContextMenu(mockContextMenu);
        controller.setup();
        // NB: controller.setup() creates a local instance of GraphRenderer
        controller.setGraphRenderer(mockGraphRenderer);
    }

    @Test
    public void shouldShowContextMenuWhenSecondaryMouseClickOccurs() {
        MouseEvent mockEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.SECONDARY, 1, true, true, true, true, true, true, true, true, true, true, null);

        controller.handleMouseClick(mockEvent);

        verify(mockContextMenu).show(any(Node.class), Matchers.eq(0.0), Matchers.eq(0.0));
    }

    @Test
    public void shouldHideContextMenuWhenPrimaryMouseKeyPressed() {
        MouseEvent mockEvent = new MouseEvent(MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);

        controller.handleMousePress(mockEvent);

        verify(mockContextMenu).hide();
    }

    @Test
    public void shouldSubscribeToVertexRemoveEvent() {
        MessageBus mockMessageBus = Mockito.mock(MessageBus.class);

        controller.setMessageBus(mockMessageBus);

        verify(mockMessageBus).subscribe(Matchers.eq("vertex.remove"), any());
    }

    @Test
    public void shouldChangeMouseCursorToHandWhenHoverOverVertex() {
        MouseEvent mockEvent = new MouseEvent(MouseEvent.MOUSE_MOVED, 150, 150, 0, 0, MouseButton.NONE, 1, true, true, true, true, true, true, true, true, true, true, null);
        VertexShape2D v1 = new VertexShape2D(150, 150, "foo");
        Set<VertexShape2D> vertexShape2DSet = new HashSet<>();

        vertexShape2DSet.add(v1);

        when(mockGraphRenderer.getVertexShapes()).thenReturn(vertexShape2DSet);

        controller.handleMouseMoved(mockEvent);

        verify(mockMessageBus).emit(Matchers.eq("Cursor.cursor.change"), Matchers.eq(Cursor.HAND.toString()));
    }

    @Test
    public void shouldAddVertexToGraphAndDispatchTheEventWhenAddingVertexAtCoords() {
        Graph<Integer, Edge<Integer>> graph = controller.getGraph();
        INotifiable vertexAddEmittedCheck = Mockito.mock(INotifiable.class);

        graph.subscribe("graph.vertex.add", vertexAddEmittedCheck);

        controller.addVertexAtCoords(10, 11);

        verify(vertexAddEmittedCheck).call(Matchers.endsWith(";x:10.0;y:11.0"));
    }

    @Test
    public void shouldSetCanvasStateToPanningAfterVertexHasBeenAdded() {
        controller.setCanvasState(CanvasStates.ADDING_VERTEX);

        controller.addVertexAtCoords(42, 43);

        assertThat(controller.getCanvasState(), is(CanvasStates.PANNING));
    }
}
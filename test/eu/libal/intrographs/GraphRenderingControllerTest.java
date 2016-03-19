package eu.libal.intrographs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

public class GraphRenderingControllerTest {

    GraphRenderingController controller;

    @Rule
    public JavaFXThreadingRule jfxRule = new JavaFXThreadingRule();

    @Mock
    private ContextMenu mockContextMenu;


    private Canvas canvas;

    private GraphicsContext ctx;

    @Mock
    private GraphRenderer<Integer, Edge<Integer>> mockGraphRenderer;

    @Before
    public void setup() {
        mockContextMenu = Mockito.mock(ContextMenu.class);

        canvas = new Canvas();
        mockGraphRenderer = Mockito.mock(GraphRenderer.class);

        controller = new GraphRenderingController();
        controller.setCanvas(canvas);
        controller.setGraphRenderer(mockGraphRenderer);
        controller.setContextMenu(mockContextMenu);
        controller.setup();

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
}
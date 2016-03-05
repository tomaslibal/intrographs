package eu.libal.intrographs;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Instant;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 */
public class GraphRenderingController implements Initializable {

    private Graph<Integer, Edge<Integer>> g;

    private GraphRenderer<Integer, Edge<Integer>> graphRenderer;
    private Canvas canvas;
    private CanvasStates canvasState;

    private MessageBus messageBus;

    /**
     * x coordinate of a mouse click
     */
    private double cx;
    /**
     * y coordinate of a mouse click
     */
    private double cy;

    /**
     * Keeps a delta x coordinate for tracking x coordinate offset between mouse click and current mouse position.
     */
    private double dx = 0;

    /**
     * Keeps a delta y coordinate for tracking y coordinate offset between mouse click and current mouse position
     */
    private double dy = 0;

    /**
     * x coordinate of the origin
     */
    private double ox = 0;
    /**
     * y coordinate of the origin
     */
    private double oy = 0;




    /*
     * When adding a new edge this auxiliary variable holds the reference to the source vertex of a new edge.
     */
    private VertexShape2D sel1;

    /**
     * When translating a vertex the vertex being translated is stored in this field on the first click. This is because
     * the vertex being re-positioned might be dragged over another vertex which would make the selection of which
     * vertex is being manipulated ambiguous.
     */
    private VertexShape2D translateVertex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void displayLabels() {
        graphRenderer.displayLabels();
    }

    public void hideLabels() {
        graphRenderer.hideLabels();
    }

    public void setup() {
        g = new Graph<>();
        g.addVertex(0, "a");
        g.addVertex(1, "b");
        g.addVertex(2, "c");
        g.addEdge("a", "b");

        graphRenderer = new GraphRenderer<>(g, canvas);
        graphRenderer.render();

        canvasState = CanvasStates.PANNING;
    }

    public void update() {
        graphRenderer.render();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setCanvasState(CanvasStates canvasState) {
        this.canvasState = canvasState;
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
    }

    public void handleMouseClick(MouseEvent event) {
        if (canvasState == CanvasStates.ADDING_VERTEX) {
            String id = String.valueOf(Instant.now().getEpochSecond());
            g.addVertex(id, event.getX() - ox, event.getY() - oy);
            canvasState = CanvasStates.PANNING;
            messageBus.emit("#addVertexBt.text.change", "Add vertex");
        }

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (canvasState == CanvasStates.ADDING_EDGE && selectedVertex.isPresent()) {

            if (sel1 == null) {
                sel1 = selectedVertex.get();
            } else {
                VertexShape2D sel2 = selectedVertex.get();
                g.addEdge(sel1.getVertexId(), sel2.getVertexId());

                sel1 = null;
                canvasState = CanvasStates.PANNING;
                messageBus.emit("#addEdgeBt.text.change", "Add vertex");
            }
        }
    }

    public void handleMouseDrag(MouseEvent event) {
        double prevDx = dx;
        double prevDy = dy;

        dx = event.getX() - cx;
        dy = event.getY() - cy;

        double tx = dx - prevDx;
        double ty = dy - prevDy;

        if (canvasState == CanvasStates.TRANSLATING_VERTEX && translateVertex != null) {
            Integer x = translateVertex.getX();
            Integer y = translateVertex.getY();
            translateVertex.setX(x + Double.valueOf( tx ).intValue());
            translateVertex.setY(y + Double.valueOf( ty ).intValue());
            graphRenderer.render();
        } else {
            ox += tx;
            oy += ty;

            graphRenderer.render(ox, oy);
        }
    }

    public void handleMousePress(MouseEvent event) {
        cx = event.getX();
        cy = event.getY();

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent() && canvasState == CanvasStates.PANNING) {
            messageBus.emit("Cursor.cursor.change", Cursor.CLOSED_HAND.toString());

            canvasState = CanvasStates.TRANSLATING_VERTEX;
            translateVertex = selectedVertex.get();

            String vertexId = selectedVertex.get().getVertexId();


            Optional<Vertex<Integer>> vertex = g.vertexSet().stream().filter(v -> v.getId().equals(vertexId)).findFirst();

            if (vertex.isPresent()) {;
                messageBus.emit("#vIDInput.text.change", vertex.get().getId());
                messageBus.emit("#vValInput.text.change", vertex.get().getValue().toString());
            }

        }

        if (!selectedVertex.isPresent() && canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
    }

    public void handleMouseRelease(MouseEvent event) {
        System.out.println("mouse released");
        dx = 0;
        dy = 0;

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            messageBus.emit("Cursor.cursor.change", Cursor.HAND.toString());
//            stage.getScene().setCursor(Cursor.HAND);
        } else {
            messageBus.emit("Cursor.cursor.change", Cursor.DEFAULT.toString());
//            stage.getScene().setCursor(Cursor.DEFAULT);
        }

        if (canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
        translateVertex = null;
    }

    public void handleMouseMoved(MouseEvent event) {
        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            messageBus.emit("Cursor.cursor.change", Cursor.HAND.toString());
        } else {
            messageBus.emit("Cursor.cursor.change", Cursor.DEFAULT.toString());
        }
    }

    public Optional<VertexShape2D> getVertexAtMouseClick(MouseEvent click) {

        int x = (int) Math.round(click.getX() - ox);
        int y = (int) Math.round(click.getY() - oy);

        int leniency = 5;
        int radius = 5;

        return graphRenderer.getVertexShapes().stream()
                .filter(shape -> {
                    int dx = Math.abs(shape.getX() - x);
                    int dy = Math.abs(shape.getY() - y);

                    return dx < (leniency + radius) && dy < (leniency + radius);
                })
                .findFirst();
    }
}

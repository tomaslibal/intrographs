package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.Coordinates2D;
import eu.libal.intrographs.presentation.shapes.TextShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 */
public class GraphRenderingController implements Initializable {

    private Graph<Integer, Edge<Integer>> g;

    private GraphRenderer<Integer, Edge<Integer>> graphRenderer;
    private Canvas canvas;
    private CanvasStates canvasState;

    private MessageBus messageBus;

    private ContextMenu contextMenu = new ContextMenu();
    private final MenuItem menuItemSpanningTree = new MenuItem("Show spanning tree");
    private final MenuItem menuItemRemoveVertex = new MenuItem("Remove vertex");
    private final MenuItem menuItemAddVertex = new MenuItem("Add vertex");

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
    private Coordinates2D lastSecondaryClickCoords;


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
        g.addVertex(3, "m");
        g.addVertex(4, "n");
        g.addVertex(5, "o");

        g.addEdge("a", "m");
        g.addEdge("a", "n");
        g.addEdge("a", "o");

        g.addEdge("b", "m");
        g.addEdge("b", "n");
        g.addEdge("b", "o");

        g.addEdge("c", "m");
        g.addEdge("c", "n");
        g.addEdge("c", "o");

        graphRenderer = new GraphRenderer<>(g, canvas);
        graphRenderer.render();

        menuItemAddVertex.setOnAction(action -> {
            addVertexAtCoords(lastSecondaryClickCoords.getX(), lastSecondaryClickCoords.getY());
        });

        menuItemRemoveVertex.setOnAction(action -> {
            getVertexAtCoordinates(
                lastSecondaryClickCoords.getX(),
                lastSecondaryClickCoords.getY()
            )
            .ifPresent(vertexShape2D -> {
                g.removeVertex(vertexShape2D.getVertexId());
            });
        });

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

    public CanvasStates getCanvasState() {
        return canvasState;
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
        subscribeToVertexDialogEvents(this.messageBus);
    }

    private void subscribeToVertexDialogEvents(MessageBus messageBus) {
        messageBus.subscribe("vertex.remove", vId -> {
            g.removeVertex(vId);
            graphRenderer.render();
        });
    }

    public void handleMouseClick(MouseEvent event) {
        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);
        MouseButton clickedButton = event.getButton();

        if (clickedButton.equals(MouseButton.SECONDARY)) {
            lastSecondaryClickCoords = new Coordinates2D(event.getX(), event.getY());
            ObservableList<MenuItem> items = contextMenu.getItems();

            if (items != null) {
                if (items.size() > 0) { items.removeAll(items); }

                if (selectedVertex.isPresent()) {
                    items.addAll(menuItemRemoveVertex, menuItemSpanningTree);
                } else {
                    items.addAll(menuItemAddVertex);
                }
            }

            contextMenu.show(canvas, event.getScreenX(), event.getScreenY());
        }

        if (canvasState == CanvasStates.ADDING_VERTEX) {
            addVertexAtCoords(event.getX(), event.getY());
        }

        if (canvasState == CanvasStates.REMOVING_VERTEX && selectedVertex.isPresent()) {
            String vertexId = selectedVertex.get().getVertexId();
            Optional<Vertex<Integer>> v = g.lookupVertex(vertexId);

            if (v.isPresent()) {
                // remove edges incident on the vertex
                LinkedList<Edge<Integer>> edgesList = new LinkedList<>();
                edgesList.addAll(g.incidentEdges(v.get()));
                g.removeEdges(edgesList);

                // remove vertex
                g.removeVertex(v.get());
            }

            canvasState = CanvasStates.PANNING;
        }

        if (canvasState == CanvasStates.ADDING_EDGE && selectedVertex.isPresent()) {

            if (sel1 == null) {
                sel1 = selectedVertex.get();
            } else {
                VertexShape2D sel2 = selectedVertex.get();
                g.addEdge(sel1.getVertexId(), sel2.getVertexId());

                sel1 = null;
                canvasState = CanvasStates.PANNING;
                messageBus.emit("#addEdgeBt.text.change", "Add edge");
            }
        }
    }

    public void addVertexAtCoords(double x, double y) {
        String id = String.valueOf(Instant.now().getEpochSecond());
        g.addVertex(id, x - ox, y - oy);
        g.dispatch("graph.vertex.add", id.concat(";x:").concat(String.valueOf(x)).concat(";y:").concat(String.valueOf(y)));
        canvasState = CanvasStates.PANNING;
        messageBus.emit("#addVertexBt.text.change", "Add vertex");
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
            TextShape2D textLabel = graphRenderer.getTextLabel(translateVertex);
            textLabel.setX(textLabel.getX() + Double.valueOf( tx ).intValue());
            textLabel.setY(textLabel.getY() + Double.valueOf( ty ).intValue());
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

        MouseButton pressedButton = event.getButton();

        if (pressedButton.equals(MouseButton.PRIMARY)) {
            contextMenu.hide();
        }

        if (selectedVertex.isPresent() && canvasState == CanvasStates.PANNING) {
            messageBus.emit("Cursor.cursor.change", Cursor.CLOSED_HAND.toString());

            canvasState = CanvasStates.TRANSLATING_VERTEX;
            translateVertex = selectedVertex.get();
            graphRenderer.setHighlightedVertex(translateVertex);

            String vertexId = selectedVertex.get().getVertexId();


            Optional<Vertex<Integer>> vertex = g.vertexSet().stream().filter(v -> v.getId().equals(vertexId)).findFirst();

            if (vertex.isPresent()) {
                messageBus.emit("#vIDInput.text.change", vertex.get().getId());

                try {
                    messageBus.emit("#vValInput.text.change", vertex.get().getValue().toString());
                } catch (NullPointerException e) {
                    // vertex has no value
                    messageBus.emit("#vValInput.text.change", "");
                }
            }

        }

        if (!selectedVertex.isPresent() && canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
    }

    public void handleMouseRelease(MouseEvent event) {
        dx = 0;
        dy = 0;

        Optional<VertexShape2D> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            messageBus.emit("Cursor.cursor.change", Cursor.HAND.toString());
            graphRenderer.setHighlightedVertex(selectedVertex.get());
            graphRenderer.render();
        } else {
            messageBus.emit("Cursor.cursor.change", Cursor.DEFAULT.toString());
            graphRenderer.setHighlightedVertex(null);
            graphRenderer.render();
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

        graphRenderer.setHighlightedVertex(selectedVertex.orElse(null));
        graphRenderer.render();
    }

    private Optional<VertexShape2D> getVertexAtMouseClick(MouseEvent click) {
        return getVertexAtCoordinates(click.getX(), click.getY());
    }

    private Optional<VertexShape2D> getVertexAtCoordinates(double dblX, double dblY) {

        int x = (int) Math.round(dblX - ox);
        int y = (int) Math.round(dblY - oy);

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

    public Set<Vertex<Integer>> getVertexSet() {
        return g.vertexSet();
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void setContextMenu(ContextMenu cm) {
        contextMenu = cm;
    }

    public void setGraphRenderer(GraphRenderer<Integer, Edge<Integer>> graphRenderer) {
        this.graphRenderer = graphRenderer;
    }

    public Graph<Integer, Edge<Integer>> getGraph() {
        return g;
    }
}

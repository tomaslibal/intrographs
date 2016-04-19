package eu.libal.intrographs.presentation.controllers;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.factories.FromAdjacencyMatrixGraphFactory;
import eu.libal.intrographs.graphs.factories.UtilityGraphFactory;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.CanvasStates;
import eu.libal.intrographs.presentation.GraphRedrawTimer;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.layout.ForceDirectedLayout;
import eu.libal.intrographs.presentation.layout.RandomGraphLayout;
import eu.libal.intrographs.presentation.shapes.Coordinates2D;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.Instant;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 *
 */
public class GraphRenderingController implements Initializable {

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

    private Graph<Integer, Edge> graph;
    private GraphRenderer<Integer, Edge> graphRenderer;

    private final Semaphore canUpdateLayout = new Semaphore(1);


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
        this.graph = UtilityGraphFactory.get();

        graphRenderer = new GraphRenderer<>(this.graph, canvas, messageBus);
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
                this.graph.removeVertex(vertexShape2D.getVertexId());
                messageBus.emit("graph.vertex.remove", vertexShape2D.getVertexId());
            });
        });

        menuItemSpanningTree.setOnAction(action -> {
//            getVertexAtCoordinates(
//                lastSecondaryClickCoords.getX(),
//                lastSecondaryClickCoords.getY()
//            )
//            .ifPresent(vertexShape2D -> {
//                SpanningTreeSearch<Integer> findSpanningTree = new SpanningTreeSearch<>();
//                Set<Vertex<Integer>> spanningTree = findSpanningTree.search(graph, vertexShape2D.getVertex(), a -> null);
//
//                if (canUpdateLayout.tryAcquire()) {
//                    SearchHighlighter<Integer> highlighter = new SearchHighlighter<>(graphRenderer, messageBus, canUpdateLayout, spanningTree);
//                    Thread layoutThread = new Thread(highlighter);
//                    layoutThread.setPriority(Thread.MIN_PRIORITY);
//                    layoutThread.start();
//                }
//            });
        });

        canvasState = CanvasStates.PANNING;
    }

    public void update() {

    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setCanvasState(CanvasStates canvasState) {
        this.canvasState = canvasState;

        if (messageBus != null) {
            messageBus.emit("cavas.state.update", canvasState.toString());
        }
    }

    public CanvasStates getCanvasState() {
        return canvasState;
    }

    public void setMessageBus(MessageBus messageBus) {
        this.messageBus = messageBus;
        subscribeToVertexDialogEvents(this.messageBus);
        subscribeToNewGraphEvents(this.messageBus);
        GraphRedrawTimer timer = new GraphRedrawTimer(graphRenderer, messageBus);
    }

    private void subscribeToNewGraphEvents(MessageBus messageBus) {
        messageBus.subscribe("new.graph.fromAdjacencyMatrix", adjMatrixString -> {
            String[] rows = adjMatrixString.split("\n");
            int[][] adjMatrix = new int[rows.length][rows.length];

            for (int i = 0; i < rows.length; i++) {
                String row = rows[i];
                String[] columns = row.split(" ");

                for (int j = 0; j < columns.length; j++) {
                    adjMatrix[i][j] = Integer.valueOf(columns[j]);
                }
            }

            Graph<Integer, Edge> graph = null;
            try {
                graph = FromAdjacencyMatrixGraphFactory.get(adjMatrix);
                setGraph(graph);
            } catch (FromAdjacencyMatrixGraphFactory.NotASquareMatrixException e) {
                e.printStackTrace();
            }
        });
    }

    private void subscribeToVertexDialogEvents(MessageBus messageBus) {
        messageBus.subscribe("vertex.remove", vId -> {
            graph.removeVertex(vId);
            messageBus.emit("renderer.update", "render");
        });

        messageBus.subscribe("vertex.color.update", msg -> {
            String[] parts = msg.split(";");
            String vId = parts[0];
            graphRenderer.getVertexShapes().stream()
                    .filter(shape -> shape.getVertex().getId().equals(vId))
                    .forEach(matchingShape -> {
                        matchingShape.setVertexColor(Color.valueOf(parts[1]));
                    });
            messageBus.emit("renderer.update", "render");
        });
    }

    public void handleMouseClick(MouseEvent event) {
        Optional<VertexShape2D<Integer>> vertexAtMouseClick = getVertexAtMouseClick(event);
        Optional<EdgeShape2D> edgeAtMouseClick = getEdgeAtMouseClick(event);
        MouseButton clickedButton = event.getButton();

        // delegate to a method
        if (clickedButton.equals(MouseButton.SECONDARY)) {
            lastSecondaryClickCoords = new Coordinates2D(event.getX(), event.getY());
            ObservableList<MenuItem> items = contextMenu.getItems();

            if (items != null) {
                if (items.size() > 0) { items.clear(); }

                if (vertexAtMouseClick.isPresent()) {
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

        // delegate to a method
        if (canvasState == CanvasStates.REMOVING_VERTEX && vertexAtMouseClick.isPresent()) {
            String vertexId = vertexAtMouseClick.get().getVertexId();
            Optional<Vertex<Integer>> v = graph.lookupVertex(vertexId);

            if (v.isPresent()) {
                // remove edges incident on the vertex
                LinkedList<Edge> edgesList = new LinkedList<>();
                edgesList.addAll(graph.incidentEdges(v.get()));
                graph.removeEdges(edgesList);

                // remove vertex
                graph.removeVertex(v.get());
                messageBus.emit("graphFile.changed", "true");
            }

            setCanvasState(CanvasStates.PANNING);
        }

        // delegate to a method
        if (canvasState == CanvasStates.ADDING_EDGE && vertexAtMouseClick.isPresent()) {

            if (sel1 == null) {
                sel1 = vertexAtMouseClick.get();
            } else {
                VertexShape2D sel2 = vertexAtMouseClick.get();
                messageBus.emit("graphFile.changed", "true");
                graph.addEdge(sel1.getVertexId(), sel2.getVertexId());

                sel1 = null;
                setCanvasState(CanvasStates.PANNING);
                messageBus.emit("#addEdgeBt.text.change", "Add edge");
            }
        }

        // delegate to a method
        if (canvasState == CanvasStates.PANNING && edgeAtMouseClick.isPresent() && !vertexAtMouseClick.isPresent()) {
            edgeAtMouseClick.get().toggleHighlight();
            messageBus.emit("renderer.update", "render");
        }
    }

    private Optional<EdgeShape2D> getEdgeAtMouseClick(MouseEvent click) {
        return getEdgeAtCoordinates(click.getX(), click.getY());
    }

    private double distSquared(Coordinates2D s, Coordinates2D t) {
        double dx = s.getX() - t.getX();
        double dy = s.getY() - t.getY();

        return (dx * dx) + (dy * dy);
    }

    private double distFromLineSegment(Coordinates2D s, Coordinates2D t, Coordinates2D p) {
        double lm = distSquared(s, t);

        if (lm == 0) {
            return Math.sqrt(distSquared(p, s));
        }

        double u = ((p.getX() - s.getX()) * (t.getX() - s.getX()) + (p.getY() - s.getY()) * (t.getY() - s.getY())) / lm;
        u = Math.max(0, Math.min(1, u));

        Coordinates2D w = new Coordinates2D(s.getX() + u * (t.getX() - s.getX()), s.getY() + u * (t.getY() - s.getY()));

        return Math.sqrt(distSquared(p, w));
    }

    private Optional<EdgeShape2D> getEdgeAtCoordinates(double dblX, double dblY) {
        Coordinates2D coords = new Coordinates2D(dblX - ox, dblY - oy);

        int leniency = 5;

        return graphRenderer.getEdgeShapes().stream()
                .filter(shape -> {
                    Coordinates2D translatedOrigin = new Coordinates2D(ox, oy);
                    Coordinates2D sourceVertex = Coordinates2D.sub(shape.getSourceVertexShape2D().getCoords(), translatedOrigin);
                    Coordinates2D targetVertex = Coordinates2D.sub(shape.getTargetVertexShape2D().getCoords(), translatedOrigin);
                    double d = distFromLineSegment(
                            sourceVertex,
                            targetVertex,
                            coords
                    );

                    return d <= leniency;
                })
                .findFirst();
    }

    public void addVertexAtCoords(double x, double y) {
        String id = String.valueOf(Instant.now().getEpochSecond());
        graph.addVertex(new Vertex<>(id));
        graph.dispatch("graph.vertex.add", id.concat(";x:").concat(String.valueOf(x)).concat(";y:").concat(String.valueOf(y)));
        setCanvasState(CanvasStates.PANNING);
        messageBus.emit("graphFile.changed", "true");
        messageBus.emit("#addVertexBt.text.change", "Add vertex");
        messageBus.emit("renderer.update", "render");
    }

    public void handleMouseDrag(MouseEvent event) {
        double prevDx = dx;
        double prevDy = dy;

        dx = event.getX() - cx;
        dy = event.getY() - cy;

        double tx = dx - prevDx;
        double ty = dy - prevDy;

        if (canvasState == CanvasStates.TRANSLATING_VERTEX && translateVertex != null) {
            Double x = translateVertex.getX();
            Double y = translateVertex.getY();
            translateVertex.setX(x + tx);
            translateVertex.setY(y + ty);
            messageBus.emit("renderer.update", "render");
        } else {
            ox += tx;
            oy += ty;

            graphRenderer.render(ox, oy);
        }
    }

    public void handleMousePress(MouseEvent event) {
        cx = event.getX();
        cy = event.getY();

        Optional<VertexShape2D<Integer>> selectedVertex = getVertexAtMouseClick(event);

        MouseButton pressedButton = event.getButton();

        if (pressedButton.equals(MouseButton.PRIMARY)) {
            contextMenu.hide();
        }

        if (selectedVertex.isPresent() && canvasState == CanvasStates.PANNING) {
            messageBus.emit("Cursor.cursor.change", Cursor.CLOSED_HAND.toString());

            canvasState = CanvasStates.TRANSLATING_VERTEX;
            translateVertex = selectedVertex.get();
            graphRenderer.setHighlightedVertex(translateVertex);

            String vertexId = selectedVertex.get().getVertex().getId();

            Optional<Vertex<Integer>> vertex = graph.vertexSet().stream().filter(v -> v.getId().equals(vertexId)).findFirst();

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

        Optional<VertexShape2D<Integer>> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            messageBus.emit("Cursor.cursor.change", Cursor.HAND.toString());
            graphRenderer.setHighlightedVertex(selectedVertex.get());
            messageBus.emit("renderer.update", "render");
        } else {
            messageBus.emit("Cursor.cursor.change", Cursor.DEFAULT.toString());
            graphRenderer.setHighlightedVertex(null);
            messageBus.emit("renderer.update", "render");
        }

        if (canvasState == CanvasStates.TRANSLATING_VERTEX) {
            canvasState = CanvasStates.PANNING;
        }
        translateVertex = null;
    }

    public void handleMouseMoved(MouseEvent event) {
        Optional<VertexShape2D<Integer>> selectedVertex = getVertexAtMouseClick(event);

        if (selectedVertex.isPresent()) {
            messageBus.emit("Cursor.cursor.change", Cursor.HAND.toString());
        } else {
            messageBus.emit("Cursor.cursor.change", Cursor.DEFAULT.toString());
        }

        graphRenderer.setHighlightedVertex(selectedVertex.orElse(null));
        messageBus.emit("renderer.update", "render");
    }

    private Optional<VertexShape2D<Integer>> getVertexAtMouseClick(MouseEvent click) {
        return getVertexAtCoordinates(click.getX(), click.getY());
    }

    private Optional<VertexShape2D<Integer>> getVertexAtCoordinates(double dblX, double dblY) {

        int x = (int) Math.round(dblX - ox);
        int y = (int) Math.round(dblY - oy);

        int leniency = 5;
        int radius = 5;

        return graphRenderer.getVertexShapes().stream()
                .filter(shape -> {
                    int dx = (int) Math.round(Math.abs(shape.getX() - x));
                    int dy = (int) Math.round(Math.abs(shape.getY() - y));

                    return dx < (leniency + radius) && dy < (leniency + radius);
                })
                .findFirst();
    }

    public Set<Vertex<Integer>> getVertexSet() {
        return graph.vertexSet();
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void setContextMenu(ContextMenu cm) {
        contextMenu = cm;
    }

    public void setGraphRenderer(GraphRenderer<Integer, Edge> graphRenderer) {
        this.graphRenderer = graphRenderer;
    }

    public GraphRenderer<Integer, Edge> getGraphRenderer() {
        return this.graphRenderer;
    }

    public Graph<Integer, Edge> getGraph() {
        return graph;
    }

    public void setGraph(Graph<Integer, Edge> graph) {
        this.graph = graph;
        graphRenderer.setGraph(graph);
        messageBus.emit("renderer.update", "render");
    }

    public void updateLayoutForceDirected() {
        if (canUpdateLayout.tryAcquire()) {
            ForceDirectedLayout forceDirectedLayout = new ForceDirectedLayout(graphRenderer, messageBus, canUpdateLayout);
            Thread layoutThread = new Thread(forceDirectedLayout);
            layoutThread.setPriority(Thread.MIN_PRIORITY);
            layoutThread.start();
        }
    }

    public void updateLayoutRandom() {
        if (canUpdateLayout.tryAcquire()) {
            RandomGraphLayout randomGraphLayout = new RandomGraphLayout(graphRenderer, messageBus, canUpdateLayout);
            Thread layoutThread = new Thread(randomGraphLayout);
            layoutThread.setPriority(Thread.MIN_PRIORITY);
            layoutThread.start();
        }
    }
}

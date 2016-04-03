package eu.libal.intrographs.presentation;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.shapes.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class GraphRenderer<T, U extends Edge<T>> {

    private Graph<T, U> graph;
    private Canvas canvas;
    private final MessageBus messageBus;
    private GraphicsContext ctx;

    private Set<EdgeShape2D> edgeShapes;
    private Map<VertexShape2D<T>, TextShape2D> verticesWithLabels;

    private VertexShape2D<T> highlightedVertex = null;

    private boolean displayLabels = false;
    private double oy;
    private double ox;

    public GraphRenderer(Graph<T, U> graph, Canvas canvas, MessageBus messageBus) {
        this.graph = graph;
        this.canvas = canvas;
        this.messageBus = messageBus;

        setup();
    }

    private void setup() {
        verticesWithLabels = createVerticesWithLabels();
        edgeShapes = createEdgeShapes();

        graph.subscribe("graph.edge.add", (String message) -> {
            String[] parts = message.split(";");

            if (parts.length != 2) {
                return;
            }

            String sourceId = parts[0].substring(7);
            String targetId = parts[1].substring(7);

            edgeShapes.add(getEdgeShape2D(sourceId, targetId));

            messageBus.emit("renderer.update", "render");
        });

        graph.subscribe("graph.vertex.add", (String message) -> {
            String[] parts = message.split(";");

            if (parts.length != 3) {
                return;
            }

            String vertexId = parts[0];
            String xCoord = parts[1].substring(2);
            String yCoord = parts[2].substring(2);

            Set<Vertex<T>> vertices = graph.vertexSet();
            Vertex<T> newVertex = vertices.stream()
                    .filter(v -> v.getId().equals(vertexId))
                    .findFirst()
                    .get();

            VertexShape2D<T> vertexShape2D = new VertexShape2D.VertexShapeBuilder<T>()
                    .withVertex(newVertex)
                    .withXCoordinate((int) Math.round(Double.parseDouble(xCoord)))
                    .withYCoordinate((int) Math.round(Double.parseDouble(yCoord)))
                    .create();

            TextShape2D label = new TextShape2D();
            label.setText(vertexShape2D.getVertexId());
            label.setX(vertexShape2D.getX() - 5);
            label.setY(vertexShape2D.getY() + 20);
            verticesWithLabels.put(vertexShape2D, label);

            messageBus.emit("renderer.update", "render");
        });

        graph.subscribe("graph.vertex.remove", (String removedVertexId) -> {

            Optional<VertexShape2D<T>> vertexToDelete = verticesWithLabels.keySet().stream()
                    .filter(vertexShape2D -> vertexShape2D.getVertexId().equals(removedVertexId))
                    .findFirst();

            if (vertexToDelete.isPresent()) {
                // remove incident edges
                edgeShapes = edgeShapes.stream()
                        .filter(edgeShape2D -> !edgeShape2D.getSourceVertexShape2D().getVertexId().equals(removedVertexId)
                                && !edgeShape2D.getTargetVertexShape2D().getVertexId().equals(removedVertexId))
                        .collect(Collectors.toSet());

                // remove the vertexShape
                verticesWithLabels.remove(vertexToDelete.get());
            }

            messageBus.emit("renderer.update", "render");
        });
    }

    public void render() {
        render(ox, oy);
    }


    public void render(double x, double y) {
        clearCanvas();
        ctx.save();
        ox = x;
        oy = y;
        ctx.translate(x, y);
        renderVertexHighlighter();
        renderVertices();
        renderEdges();
        ctx.restore();
    }

    private void renderVertexHighlighter() {
        if (ctx == null) {
            ctx = getContext2D();
        }

        if (highlightedVertex != null) {
            Coordinates2D coords = highlightedVertex.getCoords();
            Dimensions2D dims = highlightedVertex.getDims();

            VertexHighlighterShape2D highlighter = new VertexHighlighterShape2D();
            highlighter.setX( coords.getX() - Math.round( dims.getWidth() ) );
            highlighter.setY( coords.getY() - Math.round( dims.getHeight() ) );
            highlighter.setWidth( dims.getWidth() + Math.round( dims.getWidth() ) );
            highlighter.setHeight( dims.getHeight() + Math.round( dims.getHeight() ) );
            highlighter.setContext(ctx);

            try {
                highlighter.paint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void clearCanvas() {
        if (ctx == null) {
            ctx = getContext2D();
        }

        ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderBackground();
    }

    private GraphicsContext getContext2D() {
        if (ctx == null) {
            ctx = canvas.getGraphicsContext2D();
        }

        return ctx;
    }

    public Set<VertexShape2D<T>> getVertexShapes() {
        return verticesWithLabels.keySet();
    }

    public Set<EdgeShape2D> getEdgeShapes() {
        return edgeShapes;
    }

    public TextShape2D getTextLabel(VertexShape2D v) {
        return verticesWithLabels.get(v);
    }

    public Collection<TextShape2D> getLabelShapes() {
        return verticesWithLabels.values();
    }

    private void renderBackground() {
        if (ctx == null) {
            ctx = getContext2D();
        }

        ctx.setFill(Color.WHITE);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private Map<VertexShape2D<T>, TextShape2D> createVerticesWithLabels() {
        Set<Vertex<T>> vertexSet = graph.vertexSet();

        Stream<Pair<VertexShape2D<T>, TextShape2D>> pairStream = vertexSet.stream()
                .map(v -> {
                    VertexShape2D<T> vertexShape2D = VertexShape2D.VertexShapeBuilder.buildAndCreate(
                            v,
                            (int) Math.round((canvas.getWidth() / 2) + (getRandomSign())*(Math.random()*100)),
                            (int) Math.round((canvas.getHeight() / 2) + (getRandomSign())*(Math.random()*100))
                    );

                    TextShape2D label = new TextShape2D();
                    label.setContext(ctx);
                    label.setX(vertexShape2D.getX() - 5);
                    label.setY(vertexShape2D.getY() + 20);
                    label.setText(vertexShape2D.getVertexId());

                    return new Pair<>(vertexShape2D, label);
                });

        return pairStream.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private int getRandomSign() {
        return Math.random() > 0.5 ? -1 : 1;
    }

    private Set<EdgeShape2D> createEdgeShapes() {
        Set<U> edgeSet = graph.edgeSet();

        return edgeSet.stream()
                .map(e -> {
                    String sourceId = e.getSource().getId();
                    String targetId = e.getTarget().getId();

                    return getEdgeShape2D(sourceId, targetId);
                })
                .collect(Collectors.toSet());
    }

    private EdgeShape2D getEdgeShape2D(String sourceId, String targetId) {
        Optional<VertexShape2D<T>> source = getVertexShapes().stream()
                                        .filter(shape -> shape.getVertexId().equals(sourceId)).findFirst();

        Optional<VertexShape2D<T>> target = getVertexShapes().stream()
                .filter(shape -> shape.getVertexId().equals(targetId)).findFirst();

        if (source.isPresent() && target.isPresent()) {
            return EdgeShape2D.EdgeShapeBuilder.buildAndCreate(source.get(), target.get());
        } else {
            return EdgeShape2D.EdgeShapeBuilder.buildAndCreate(null, null);
        }
    }

    private void renderVertices() {
        verticesWithLabels.forEach((vertex, label) -> {
            try {
                vertex.setContext(ctx);
                vertex.paint();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (displayLabels) {
                try {
                    label.setContext(ctx);
                    label.paint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void renderEdges() {
        edgeShapes.forEach(shape -> {
            try {
                shape.setContext(ctx);
                shape.paint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void displayLabels() {
        displayLabels = true;
        render();
    }

    public void hideLabels() {
        displayLabels = false;
        render();
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setHighlightedVertex(VertexShape2D highlightedVertex) {
        this.highlightedVertex = highlightedVertex;
    }

    public VertexShape2D getHighlightedVertex() {
        return highlightedVertex;
    }

    public void setGraph(Graph<T, U> graph) {
        this.graph = graph;
        setup();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

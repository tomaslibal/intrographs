package eu.libal.intrographs.presentation;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.TextShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
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
    private GraphicsContext ctx;

    private Set<EdgeShape2D> edgeShapes;
    private Map<VertexShape2D, TextShape2D> verticesWithLabels;

    private boolean displayLabels = false;

    public GraphRenderer(Graph<T, U> graph, Canvas canvas) {
        this.graph = graph;
        this.canvas = canvas;

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

            render();
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

            VertexShape2D vertexShape2D = new VertexShapeBuilder()
                    .build(newVertex)
                    .setX((int) Math.round(Double.parseDouble(xCoord)))
                    .setY((int) Math.round(Double.parseDouble(yCoord)))
                    .create();

            TextShape2D label = new TextShape2D();
            label.setText(vertexShape2D.getVertexId());
            label.setX(vertexShape2D.getX() - 5);
            label.setY(vertexShape2D.getY() + 20);
            verticesWithLabels.put(vertexShape2D, label);

            render();
        });
    }

    public void render() {
        clearCanvas();
        renderVertices();
        renderEdges();
    }

    public void clearCanvas() {
        if (ctx == null) {
            ctx = getContext2D();
        }

        ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderBackground();
    }

    public GraphicsContext getContext2D() {
        if (ctx == null) {
            ctx = canvas.getGraphicsContext2D();
        }
        return ctx;
    }

    public Set<VertexShape2D> getVertexShapes() {
        return verticesWithLabels.keySet();
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

    private Map<VertexShape2D, TextShape2D> createVerticesWithLabels() {
        Set<Vertex<T>> vertexSet = graph.vertexSet();

        Stream<Pair<VertexShape2D, TextShape2D>> pairStream = vertexSet.stream()
                .map(v -> {
                    VertexShape2D vertexShape2D = VertexShapeBuilder.buildAndCreate(v);

                    TextShape2D label = new TextShape2D();
                    label.setContext(ctx);
                    label.setX(vertexShape2D.getX() - 5);
                    label.setY(vertexShape2D.getY() + 20);
                    label.setText(vertexShape2D.getVertexId());

                    return new Pair<>(vertexShape2D, label);
                });

        return pairStream.collect(Collectors.toMap(Pair::getKey, Pair::getValue));
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
        Optional<VertexShape2D> source = getVertexShapes().stream()
                                        .filter(shape -> shape.getVertexId().equals(sourceId)).findFirst();

        Optional<VertexShape2D> target = getVertexShapes().stream()
                .filter(shape -> shape.getVertexId().equals(targetId)).findFirst();

        if (source.isPresent() && target.isPresent()) {
            return EdgeShapeBuilder.buildAndCreate(source.get(), target.get());
        } else {
            return EdgeShapeBuilder.buildAndCreate(null, null);
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
                shape.setContext(getContext2D());
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

    private static class VertexShapeBuilder {
        private int x = 0;
        private int y = 0;
        private IVertex v;

        public VertexShapeBuilder build(IVertex v) {
            this.v = v;
            return this;
        }

        public VertexShapeBuilder setX(int x) {
            this.x = x;
            return this;
        }

        public VertexShapeBuilder setY(int y) {
            this.y = y;
            return this;
        }

        public VertexShape2D create() {
            return new VertexShape2D(x, y, v.getId());
        }

        public static <VertexType> VertexShape2D buildAndCreate(Vertex<VertexType> v) {
            return new VertexShape2D((int) Math.round(Math.random()*100), (int) Math.round(Math.random()*100), v.getId());
        }
    }

    private static class EdgeShapeBuilder {
        public static EdgeShape2D buildAndCreate(VertexShape2D source, VertexShape2D target) {
            return new EdgeShape2D(source, target);
        }
    }
}

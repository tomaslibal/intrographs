package eu.libal.intrographs.presentation;

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
import java.util.concurrent.Semaphore;
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

    private VertexShape2D highlightedVertex = null;

    private boolean displayLabels = false;
    private double oy;
    private double ox;

    private final Semaphore sem_render;

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

            VertexShape2D vertexShape2D = new VertexShape2D.VertexShapeBuilder()
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

        graph.subscribe("graph.vertex.remove", (String message) -> {
            String vertexId = message;

            Optional<VertexShape2D> vertexToDelete = verticesWithLabels.keySet().stream()
                    .filter(vertexShape2D -> vertexShape2D.getVertexId().equals(vertexId))
                    .findFirst();

            if (vertexToDelete.isPresent()) {
                // remove incident edges
                edgeShapes = edgeShapes.stream()
                        .filter(edgeShape2D -> !edgeShape2D.getSourceId().getVertexId().equals(vertexId)
                                 && !edgeShape2D.getTargetId().getVertexId().equals(vertexId))
                        .collect(Collectors.toSet());

                Optional<Vertex<T>> vertexObj = graph.lookupVertex(vertexId);
                graph.incidentEdges(vertexObj.get());

                // remove the vertex
                verticesWithLabels.remove(vertexToDelete.get());
            }

            render();
        });

        sem_render = new Semaphore(1);
    }

    public void render() {
        render(ox, oy);
    }


    public void render(double x, double y) {

//        Thread bgThreadRender = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                clearCanvas();
//                ctx.save();
//                ox = x;
//                oy = y;
//                ctx.translate(x, y);
//                renderVertices();
//                renderEdges();
//                ctx.restore();
//                sem_render.release();
//            }
//        });
//
//        try {
//            sem_render.acquire();
//            bgThreadRender.start();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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

    private Map<VertexShape2D, TextShape2D> createVerticesWithLabels() {
        Set<Vertex<T>> vertexSet = graph.vertexSet();

        Stream<Pair<VertexShape2D, TextShape2D>> pairStream = vertexSet.stream()
                .map(v -> {
                    VertexShape2D vertexShape2D = VertexShape2D.VertexShapeBuilder.buildAndCreate(v);

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

    public void setHighlightedVertex(VertexShape2D highlightedVertex) {
        this.highlightedVertex = highlightedVertex;
    }

    public VertexShape2D getHighlightedVertex() {
        return highlightedVertex;
    }

    private static class EdgeShapeBuilder {
        public static EdgeShape2D buildAndCreate(VertexShape2D source, VertexShape2D target) {
            return new EdgeShape2D(source, target);
        }
    }
}

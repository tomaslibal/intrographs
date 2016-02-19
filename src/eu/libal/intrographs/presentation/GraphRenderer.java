package eu.libal.intrographs.presentation;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

/**
 *
 */
public class GraphRenderer<VertexType, EdgeClass> {

    private Graph graph;
    private Canvas canvas;
    private GraphicsContext ctx;

    private Set<VertexShape2D> vertexShapes;
    private Set<EdgeShape2D> edgeShapes;

    public GraphRenderer(Graph graph, Canvas canvas) {
        this.graph = graph;
        this.canvas = canvas;

        vertexShapes = createVertexShapes();
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

            Set<Vertex<VertexType>> vertices = graph.vertexSet();
            Vertex<VertexType> newVertex = vertices.stream()
                    .filter(v -> v.getId().equals(vertexId))
                    .findFirst()
                    .get();

            VertexShape2D vertexShape2D = new VertexShapeBuilder()
                    .build(newVertex)
                    .setX((int) Math.round(Double.parseDouble(xCoord)))
                    .setY((int) Math.round(Double.parseDouble(yCoord)))
                    .create();

            vertexShapes.add(
                    vertexShape2D
            );

            render();
        });
    }

    public void render() {
        renderVertices();
        renderEdges();
    }

    public GraphicsContext getContext2D() {
        if (ctx == null) {
            ctx = canvas.getGraphicsContext2D();
        }
        return ctx;
    }

    public Set<VertexShape2D> getVertexShapes() {
        return vertexShapes;
    }

    private Set<VertexShape2D> createVertexShapes() {
        Set<Vertex<VertexType>> vertexSet = graph.vertexSet();

        return vertexSet.stream()
                .map(VertexShapeBuilder::buildAndCreate)
                .collect(Collectors.toSet());
    }

    private Set<EdgeShape2D> createEdgeShapes() {
        Set<EdgeClass> edgeSet = graph.edgeSet();

        return edgeSet.stream()
                .map(e -> {
                    String sourceId = ((Edge<VertexType>) e).getSource().getId();
                    String targetId = ((Edge<VertexType>) e).getTarget().getId();

                    return getEdgeShape2D(sourceId, targetId);
                })
                .collect(Collectors.toSet());
    }

    private EdgeShape2D getEdgeShape2D(String sourceId, String targetId) {
        Optional<VertexShape2D> source = vertexShapes.stream()
                                        .filter(shape -> shape.getVertexId().equals(sourceId)).findFirst();

        Optional<VertexShape2D> target = vertexShapes.stream()
                .filter(shape -> shape.getVertexId().equals(targetId)).findFirst();

        if (source.isPresent() && target.isPresent()) {
            return EdgeShapeBuilder.buildAndCreate(source.get(), target.get());
        } else {
            return EdgeShapeBuilder.buildAndCreate(null, null);
        }
    }

    private void renderVertices() {
        vertexShapes.forEach(shape -> {
            try {
                shape.setContext(getContext2D());
                shape.paint();
            } catch (Exception e) {
                e.printStackTrace();
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

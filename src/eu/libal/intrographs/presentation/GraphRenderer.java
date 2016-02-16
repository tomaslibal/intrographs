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

                    Optional<VertexShape2D> source = vertexShapes.stream()
                                                    .filter(shape -> shape.getVertexId().equals(sourceId)).findFirst();

                    Optional<VertexShape2D> target = vertexShapes.stream()
                            .filter(shape -> shape.getVertexId().equals(targetId)).findFirst();

                    if (source.isPresent() && target.isPresent()) {
                        return EdgeShapeBuilder.buildAndCreate(source.get(), target.get());
                    } else {
                        return EdgeShapeBuilder.buildAndCreate(null, null);
                    }
                })
                .collect(Collectors.toSet());
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
        public VertexShapeBuilder build(IVertex v) {
            return this;
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

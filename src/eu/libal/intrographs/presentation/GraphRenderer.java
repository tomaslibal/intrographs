package eu.libal.intrographs.presentation;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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
                .map(v -> VertexShapeBuilder.buildAndCreate(v))
                .collect(Collectors.toSet());
    }

    private Set<EdgeShape2D> createEdgeShapes() {
        Set<EdgeClass> edgeSet = graph.edgeSet();

        return edgeSet.stream()
                .map(e -> EdgeShapeBuilder.buildAndCreate(e))
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
            return new VertexShape2D((int) Math.round(Math.random()*100), (int) Math.round(Math.random()*100));
        }
    }

    private static class EdgeShapeBuilder {
        public static <EdgeClass> EdgeShape2D buildAndCreate(EdgeClass e) {
            return new EdgeShape2D();
        }
    }
}

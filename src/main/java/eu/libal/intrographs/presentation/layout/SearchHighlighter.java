package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.EdgeShape2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SearchHighlighter<T> implements Runnable {

    private final GraphRenderer<Integer, Edge> graphRenderer;
    private final MessageBus messageBus;
    private final Semaphore canUpdateLayout;

    private final Set<Vertex<T>> verticesToHighlight;

    public SearchHighlighter(GraphRenderer<Integer, Edge> graphRenderer, MessageBus messageBus, Semaphore canUpdateLayout, Set<Vertex<T>> verticesToHighlight) {
        this.graphRenderer = graphRenderer;
        this.messageBus = messageBus;
        this.canUpdateLayout = canUpdateLayout;
        this.verticesToHighlight = verticesToHighlight;
    }

    @Override
    public void run() {
        Set<VertexShape2D<Integer>> vertexShapes = graphRenderer.getVertexShapes();
        Set<EdgeShape2D> edgeShapes = graphRenderer.getEdgeShapes();

        edgeShapes.forEach(edgeShape2D -> edgeShape2D.setHighlighted(false));

        VertexShape2D<Integer> last = null;

        for (Iterator<VertexShape2D<Integer>> it = vertexShapes.iterator(); it.hasNext(); ) {
            VertexShape2D<Integer> source = last != null ? last : it.next();

            if (it.hasNext()) {
                VertexShape2D<Integer> target = it.next();
                last = target;

                Optional<EdgeShape2D> matchingEdge = edgeShapes.stream()
                        .filter(edge -> (edge.getSourceVertexShape2D().getVertex().equals(source.getVertex()) &&
                                        edge.getTargetVertexShape2D().getVertex().equals(target.getVertex())) ||
                                        (edge.getTargetVertexShape2D().getVertex().equals(source.getVertex()) &&
                                        edge.getSourceVertexShape2D().getVertex().equals(target.getVertex()))).findFirst();

                if (matchingEdge.isPresent()) {
                    matchingEdge.get().setHighlighted(true);
                }
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageBus.emit("renderer.update", "render");
        }

        canUpdateLayout.release();
    }
}

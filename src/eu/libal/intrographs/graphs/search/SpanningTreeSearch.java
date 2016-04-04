package eu.libal.intrographs.graphs.search;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 *
 */
public class SpanningTreeSearch<T> implements IGraphTraversingSearch<T> {

    private Set<String> visitedVertices;
    private Set<Vertex<T>> tree;

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode) {
        /**
         * find random starting vertex
         */
        graph.vertexSet().stream().findAny().ifPresent(v -> {
            search(graph, v, execForEachNode);
        });

        return tree;
    }

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Vertex<T> v, Function<Vertex<T>, ?> execForEachNode) {
        visitedVertices = new HashSet<>();
        tree = new HashSet<>();

        visitedVertices.add(v.getId());
        tree.add(v);
        branchFromVertex(graph, v);

        return tree;
    }

    private void branchFromVertex(Graph<T, ?> graph, Vertex<T> v) {
        Set<Vertex<T>> adjacentVertices = v.getAdjacentVertices();

        for (Vertex<T> adj : adjacentVertices) {
            if (!visitedVertices.contains(adj.getId())) {
                visitedVertices.add(adj.getId());
                tree.add(adj);
                branchFromVertex(graph, adj);
            }
        }
    }
}

package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 *
 */
public class SpanningTreeSearch<T> implements IGraphTraversingSearch<T> {

    Set<String> visitedVertices;
    private Set<Vertex<T>> tree;

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode) {
        visitedVertices = new HashSet<>();
        tree = new HashSet<>();

        graph.vertexSet().stream().findAny().ifPresent(v -> {
            visitedVertices.add(v.getId());
            tree.add(v);
            branchFromVertex(graph, v);
        });

        return tree;
    }

    private void branchFromVertex(Graph<T, ?> graph, Vertex<T> v) {
        Set<IVertex<T>> adjacentVertices = v.getAdjacentVertices();

        for (IVertex<T> adj : adjacentVertices) {
            if (!visitedVertices.contains(adj.getId())) {
                visitedVertices.add(adj.getId());
                tree.add((Vertex<T>) adj);
                branchFromVertex(graph, (Vertex<T>) adj);
            }
        }
    }
}

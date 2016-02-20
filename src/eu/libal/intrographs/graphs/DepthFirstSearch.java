package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.*;
import java.util.function.Function;

/**
 *
 */
public class DepthFirstSearch<T> implements IGraphTraversingSearch<T> {

    /**
     * One to one mapping between graph's IVertex vertices and TraversableVertex vertices used by DFS algorithm. DFS
     * needs to know if it has visited a vertex. This could be refactored using a set of String IDs of visited vertices
     * without the need for a special class.
     */
    private Set<TraversableVertex<T>> vertices;

    /**
     * A stack of all graph's nodes. When the stack is empty all nodes would have been visited.
     */
    private ArrayDeque<Vertex<T>> stack;


    @Override
    public void search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode) {
        vertices = new HashSet<>();
        stack = new ArrayDeque<>();

        graph.vertexSet()
                .forEach(v -> {
                    vertices.add(new TraversableVertex<>(v));
                    stack.push(v);
                });

        while (stack.size() > 0) {
            Vertex<T> v = stack.pop();
            visitVertex(execForEachNode, v);
        }
    }

    private void visitVertex(Function<Vertex<T>, ?> execForEachNode, Vertex<T> v) {
        Optional<TraversableVertex<T>> vertexOptional = lookupVertex(v);

        if (!vertexOptional.isPresent()) {
            return;
        }

        TraversableVertex<T> vertex = vertexOptional.get();

        if (vertex.visited()) {
            return;
        }

        vertex.markAsVisited();

        execForEachNode.apply(v);

        Set<IVertex<T>> adjacentVertices = v.getAdjacentVertices();

        for (IVertex<T> adj : adjacentVertices) {
            Optional<TraversableVertex<T>> traversableOptional = lookupVertex((Vertex<T>) adj);
            if (traversableOptional.isPresent()) {
                TraversableVertex<T> traversable = traversableOptional.get();
                if (!traversable.visited()) {
                    visitVertex(execForEachNode, traversable);
                }
            }
        }
    }

    private Optional<TraversableVertex<T>> lookupVertex(Vertex<T> v) {
        return vertices.stream()
                .filter(traversableVertex -> traversableVertex.compareTo(v) == 0)
                .findFirst();
    }

    private class TraversableVertex<U> extends Vertex<U> {

        private boolean visited = false;

        public TraversableVertex(Vertex<U> v) {
            super(v.getValue(), v.getId());

            // replace with a copy for speed gain
            v.getAdjacentVertices()
                    .stream()
                    .forEach(this::addAdjacentVertex);
        }

        public boolean visited() {
            return visited;
        }

        public void markAsVisited() {
            visited = true;
        }
    }
}

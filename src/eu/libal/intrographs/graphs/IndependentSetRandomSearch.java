package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class IndependentSetRandomSearch<T> implements IGraphTraversingSearch<T> {

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode) {
        Optional<Vertex<T>> randomVertex = graph.vertexSet()
                .stream()
                .findAny();

        if (randomVertex.isPresent()) {
            return search(graph, randomVertex.get(), execForEachNode);
        } else {
            return null;
        }
    }

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Vertex<T> v, Function<Vertex<T>, ?> execForEachNode) {
        return null;
    }
}

package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.function.Function;

/**
 *
 */
public interface IGraphTraversingSearch<T> {

    void search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode);
}

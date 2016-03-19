package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.util.List;

public class AdjacencyList<T> {

    List<Pair<Vertex<T>, Vertex<T>>> getList() {
        return null;
    }

    boolean add(Vertex<T> m, Vertex<T> n) {
        return false;
    }

    boolean add(Pair<Vertex<T>, Vertex<T>> adjacent) {
        return false;
    }

    boolean isAdjacentTo(Vertex<T> m, Vertex<T> n) {
        return false;
    }

    boolean remove(Vertex<T> m, Vertex<T> n) {
        return false;
    }

    boolean remove(Pair<Vertex<T>, Vertex<T>> adjacent) {
        return false;
    }
}

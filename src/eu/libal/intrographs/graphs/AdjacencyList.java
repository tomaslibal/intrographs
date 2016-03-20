package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AdjacencyList<T> {

    private final Map<Vertex<T>, List<Vertex<T>>> adjList = new LinkedHashMap<>();

    Map<Vertex<T>, List<Vertex<T>>> getList() {
        return adjList;
    }

    boolean add(Vertex<T> m) {

        if (!adjList.containsKey(m)) {
            adjList.put(m, new LinkedList<>());
            return true;
        }

        return false;
    }

    boolean add(Vertex<T> m, Vertex<T> n) {
        add(m);

        return adjList.get(m).add(n);
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

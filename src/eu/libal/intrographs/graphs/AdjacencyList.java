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
        List<Vertex<T>> adjM = adjList.get(m);
        List<Vertex<T>> adjN = adjList.get(n);
        boolean result = adjM != null && adjM.contains(n);
        boolean result2 = adjN != null && adjN.contains(m);
        return result || result2;
    }

    /**
     * Removes vertex m from the map altogether with its list of adjacent nodes
     *
     * @param m
     * @return true or false if depending on whether m was removed or not.
     */
    boolean remove(Vertex<T> m) { return false; }

    /**
     * Removes n from the adjancency list of m, keeping m in the map of nodes
     *
     * @param m
     * @param n
     * @return
     */
    boolean remove(Vertex<T> m, Vertex<T> n) {
        return false;
    }

    /**
     * Argument adjacent is a pair of a key (Vertex of T) and a value (Vertex of T). This function removes the value node
     * from the list of adjacent nodes of the key node.
     *
     * @param adjacent
     * @return
     */
    boolean remove(Pair<Vertex<T>, Vertex<T>> adjacent) {
        return false;
    }
}

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
        add(n);

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
     * @param m Vertex to be removed
     * @return true or false depending on whether m was removed or not.
     */
    List<Vertex<T>> remove(Vertex<T> m) {
        return adjList.remove(m);
    }

    /**
     * Removes n from the adjancency list of m, keeping m in the map of nodes
     *
     * @param m vertex whose adjacency list if being updated
     * @param n vertex to be removed from the adjacency list
     * @return true or false depending on whether n was successfully removed or not
     */
    boolean remove(Vertex<T> m, Vertex<T> n) {
        return false;
    }

    /**
     * Argument adjacent is a pair of a key (Vertex of T) and a value (Vertex of T). This function removes the value node
     * from the list of adjacent nodes of the key node.
     *
     * @param adjacent adjacency pair to be removed
     * @return true of false depending on whether adjacent.getValue() was successfully removed from adjacent.getKey()
     */
    boolean remove(Pair<Vertex<T>, Vertex<T>> adjacent) {
        return false;
    }
}

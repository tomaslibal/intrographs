package eu.libal.intrographs.graphs;

import eu.libal.intrographs.common.IListenable;
import eu.libal.intrographs.common.INotifiable;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

abstract public class BaseGraph<T, U extends Edge<T>> implements IGraph<T, U>, IListenable {

    protected final Set<Vertex<T>> vertices = new HashSet<>();
    protected final Set<U> edges = new HashSet<>();

    protected List<Pair<String, INotifiable>> callbacks = new LinkedList<>();

    public int degreeOf(Vertex<T> v) {
        int d = 0;

        for (U e : edges) {
            if (e.getSource().equals(v) || e.getTarget().equals(v)) {
                d++;
            }
        }

        return d;
    }

    public int degreeOf(String vertexId) {
        Optional<Vertex<T>> vertexById = getVertexById(vertexId);

        if (vertexById.isPresent()) {
            return degreeOf(vertexById.get());
        } else {
            return -1;
        }
    }

    public void subscribe(String eventName, INotifiable callback) {
        callbacks.add(new Pair<>(eventName, callback));
    }

    protected void dispatch(String eventName, String message) {
        callbacks.stream()
            .filter(pair -> pair.getKey().equals(eventName))
            .forEach(pair -> {
                try {
                    pair.getValue().call(message);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            });
    }

    /**
     *
     * @return new generated String id
     */
    protected String getNewId() {
        return "id" + Date.from(Instant.now()).toString();
    }

    protected Optional<Vertex<T>> getVertexById(String id) {
        return vertices.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    @Override
    public Set<U> incidentEdges(Vertex<T> v) {
        return edges.stream()
                .filter(e -> e.getSource().getId().equals(v.getId()) || e.getTarget().getId().equals(v.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    /**
     *
     * A graph keeps vertices in a set and each vertex keeps a list of adjacent vertices. Thus, the edges are embedded in
     * this adjacency list. However, the graph also keeps a separate set of Edges, which is a set representing the same
     * state captured by the adjacency list. This is so that the edge set does not have to be computed every time it
     * is requested.
     *
     * @return Set&lt;U&gt;
     */
    public Set<U> edgeSet() {
        return edges;
    }

    @Override
    public Set<Vertex<T>> vertexSet() {
        return vertices;
    }
}

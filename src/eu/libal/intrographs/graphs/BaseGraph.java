package eu.libal.intrographs.graphs;

import eu.libal.intrographs.common.IListenable;
import eu.libal.intrographs.common.INotifiable;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

abstract public class BaseGraph<T, U extends Edge> implements IGraph<T, U>, IListenable {
    protected Set<Vertex<T>> vertices;
    protected Set<U> edges;

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
        return Date.from(Instant.now()).toString();
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
}

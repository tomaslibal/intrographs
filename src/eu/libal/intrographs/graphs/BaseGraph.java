package eu.libal.intrographs.graphs;

import eu.libal.intrographs.common.IListenable;
import eu.libal.intrographs.common.INotifiable;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import javafx.util.Pair;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

abstract public class BaseGraph<VertexType, EdgeClass extends Edge> implements IGraph<VertexType, EdgeClass>, IListenable {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<EdgeClass> edges;

    protected List<Pair<String, INotifiable>> callbacks = new LinkedList<>();

    public int degreeOf(Vertex<VertexType> v) {
        int d = 0;

        for (Iterator<Edge<VertexType>> it = (Iterator<Edge<VertexType>>) edges.iterator(); it.hasNext(); ) {
            Edge<VertexType> e = it.next();
            if (e.getSource().equals(v) || e.getTarget().equals(v)) {
                d++;
            }
        }

        return d;
    }

    public int degreeOf(String vertexId) {
        Optional<Vertex<VertexType>> vertexById = getVertexById(vertexId);

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

    protected Optional<Vertex<VertexType>> getVertexById(String id) {
        return vertices.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    @Override
    public Set<EdgeClass> incidentEdges(Vertex<VertexType> v) {
        return edges.stream()
                .filter(e -> e.getSource().getId().equals(v.getId()) || e.getTarget().getId().equals(v.getId()))
                .collect(Collectors.toSet());
    }
}

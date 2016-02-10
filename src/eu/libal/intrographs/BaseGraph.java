package eu.libal.intrographs;

import java.time.Instant;
import java.util.*;

abstract public class BaseGraph<VertexType, EdgeClass> implements IGraph<VertexType, EdgeClass> {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<EdgeClass> edges;

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
}

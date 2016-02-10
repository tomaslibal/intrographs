package eu.libal.intrographs;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

abstract public class BaseGraph<VertexType, EdgeClass> implements IGraph<VertexType, EdgeClass> {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<EdgeClass> edges;

    /**
     *
     * @return new generated String id
     */
    protected String getNewId() {
        return Date.from(Instant.now()).toString();
    }
}

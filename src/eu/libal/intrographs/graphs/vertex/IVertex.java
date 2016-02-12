package eu.libal.intrographs.graphs.vertex;

/**
 * Vertex represents a node in a graph.
 *
 */
public interface IVertex<T>
{
    /**
     * This method should return the value member of the vertex
     *
     * @return T value
     */
    T getValue();

    /**
     *
     * @return String id
     */
    String getId();
}

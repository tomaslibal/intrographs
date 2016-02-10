package eu.libal.intrographs.vertex;

/**
 * Created by tlibal on 2/9/16.
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

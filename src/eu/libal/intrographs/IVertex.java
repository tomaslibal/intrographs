package eu.libal.intrographs;

/**
 * Created by tlibal on 2/9/16.
 */
public interface IVertex<T>
{
    T getValue();

    /**
     *
     * @return String id
     */
    String getId();
}

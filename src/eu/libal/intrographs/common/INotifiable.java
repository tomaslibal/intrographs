package eu.libal.intrographs.common;

/**
 *
 */
@FunctionalInterface
public interface INotifiable {
    void call(String message);
}

package eu.libal.intrographs.common;

/**
 * A functional interface for callbacks that take a string message when invoked.
 *
 */
@FunctionalInterface
public interface Notifiable {

    /**
     * When invoked, a string message is passed as the only argument
     *
     * @param message message from the invoking object
     */
    void call(String message);
}

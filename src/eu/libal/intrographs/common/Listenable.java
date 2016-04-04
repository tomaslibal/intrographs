package eu.libal.intrographs.common;

/**
 * Classes implementing this interface must implement a subscribe method. This method should allow other objects to
 * attach a listener/callback which should be invoked when the given eventName occurs in the object implementing this
 * interface.
 *
 */
public interface Listenable {

    /**
     * Lets other objects to subscribe to events.
     *
     * @param eventName event name
     * @param callback a callback which should be invoked should the given event happen
     */
    void subscribe(String eventName, Notifiable callback);
}

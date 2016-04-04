package eu.libal.intrographs.common;

/**
 *
 */
public interface IListenable {
    void subscribe(String eventName, Notifiable callback);
}

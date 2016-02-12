package eu.libal.intrographs.common;

import java.lang.reflect.Method;

/**
 *
 */
public interface IListenable {
    void subscribe(String eventName, INotifiable callback);
}

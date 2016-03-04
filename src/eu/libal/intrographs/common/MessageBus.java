package eu.libal.intrographs.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MessageBus implements IListenable {

    Map<String, List<INotifiable>> listeners;

    public MessageBus() {
        listeners = new LinkedHashMap<>();
    }

    @Override
    public void subscribe(String eventName, INotifiable callback) {
        listeners.get(eventName).add(callback);
    }

    public void emit(String eventName, String value) {
        listeners.get(eventName)
                .forEach(callback -> callback.call(value));
    }
}

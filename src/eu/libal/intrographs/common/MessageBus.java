package eu.libal.intrographs.common;

import java.util.LinkedHashMap;
import java.util.LinkedList;
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
        List<INotifiable> iNotifiables = listeners.get(eventName);
        if (iNotifiables == null) {
            iNotifiables = new LinkedList<>();
        }
        iNotifiables.add(callback);

        listeners.put(eventName, iNotifiables);
    }

    public void emit(String eventName, String value) {
        List<INotifiable> iNotifiables = listeners.get(eventName);
        if (iNotifiables != null) {
            iNotifiables
                    .forEach(callback -> callback.call(value));
        }
    }
}

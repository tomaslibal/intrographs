package eu.libal.intrographs.common;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Message bus lets external objects to subscribe to events (by eventName) and it also lets external objects
 * emit events with a value. Each listeners is notified when a new message is emitted.
 *
 * @version 1.0.0
 */
public class MessageBus implements Listenable {

    private final Map<String, List<Notifiable>> listeners;

    public MessageBus() {
        listeners = new LinkedHashMap<>();
    }

    @Override
    public void subscribe(String eventName, Notifiable callback) {
        List<Notifiable> notifiables = listeners.get(eventName);

        if (notifiables == null) {
            notifiables = new LinkedList<>();
        }

        notifiables.add(callback);

        listeners.put(eventName, notifiables);
    }

    public void emit(String eventName, String value) {
        List<Notifiable> notifiables = listeners.get(eventName);

        if (notifiables != null) {
            notifiables.forEach(callback -> {
                try {
                    callback.call(value);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

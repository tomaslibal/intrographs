package eu.libal.intrographs.common;

import java.util.*;

public class ListenableField<T> implements IListenable {

    private T value = null;
    private final Map<String, List<INotifiable>> callbacks;

    public ListenableField(T value) {
        this();
        setValue(value);
    }

    public ListenableField() {
        callbacks = new LinkedHashMap<>();
        callbacks.put("update", new LinkedList<>());
    }

    @Override
    public void subscribe(String eventName, INotifiable callback) {
        if (eventName.equals("update")) {
            callbacks.get("update").add(callback);
        }
    }

    public void setValue(T value) {
        this.value = value;
        callbacks.get("update").forEach(fn -> fn.call(value.toString()));
    }

    public T getValue() {
        return value;
    }
}
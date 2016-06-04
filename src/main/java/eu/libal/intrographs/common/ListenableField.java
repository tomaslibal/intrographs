package eu.libal.intrographs.common;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ListenableField<T> implements Listenable, Serializable {

    private static final long serialVersionUID = 1421234567890L;

    private T value = null;
    private HashMap<String, List<Notifiable>> callbacks;

    public ListenableField(T value) {
        this();
        setValue(value);
    }

    public ListenableField() {
        callbacks = new LinkedHashMap<>();
        callbacks.put("update", new LinkedList<>());
    }

    @Override
    public void subscribe(String eventName, Notifiable callback) {
        if (eventName.equals("update")) {
            callbacks.get("update").add(callback);
        }
    }

    public void setValue(T value) {
        this.value = value;
        callbacks.get("update").forEach(fn -> {
            try {
                fn.call(value.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public T getValue() {
        return value;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(value);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        value = (T) in.readObject();
        callbacks = new LinkedHashMap<>();
        callbacks.put("update", new LinkedList<>());
    }

    private void readObjectNoData() throws ObjectStreamException {

    }
}

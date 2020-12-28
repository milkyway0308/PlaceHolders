package skywolf46.placeholders.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageParameters {
    private HashMap<Class, List<Object>> map = new HashMap<>();
    private HashMap<String, Object> namedVariable = new HashMap<>();

    public static MessageParameters of(Object... o) {
        MessageParameters storage = new MessageParameters();
        for (Object x : o)
            storage.add(x);
        return storage;
    }

    public MessageParameters add(Object o) {
        ClassUtil.iterateParentClass(o.getClass(), cl -> {
            map.computeIfAbsent(cl, a -> new ArrayList<>()).add(o);
        });
        return this;
    }

    public MessageParameters add(String name, Object o, boolean addWithClass) {
        if (addWithClass) {
            add(o);
        }
        namedVariable.put(name, o);
        return this;
    }

    public MessageParameters add(String name, Object o) {
        return add(name, o, false);
    }

    public <T> T get(Class cx) {
//        System.out.println("Class: " + map);
        return map.containsKey(cx) ? (T) map.get(cx).get(0) : null;
    }

    public <T> List<T> getAll(Class cx) {
        return map.containsKey(cx) ? (List<T>) map.get(cx) : new ArrayList<>();
    }

    public <T> T get(String cx) {
        return (T) namedVariable.get(cx);
    }

    public List<String> getKeys() {
        return new ArrayList<>(namedVariable.keySet());
    }
}

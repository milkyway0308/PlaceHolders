package skywolf46.messagereplacer;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.messagereplacer.data.ReplaceableStringList;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class MessageReplacer {
    private static HashMap<Class, MessageReplacer> replacers = new HashMap<>();
    private HashMap<String, ReplaceableStringList> sList = new HashMap<>();
    private HashSet<String> alerted = new HashSet<>();



    public static MessageReplacer register(Class pl, File fx) {
        MessageReplacer mr = of(fx);
        replacers.put(pl, mr);
        return mr;
    }

    public static MessageReplacer of(File f) {
        MessageReplacer mr = new MessageReplacer();
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(f);
        for (String x : conf.getKeys(false)) {
            if (conf.isList(x)) {
                mr.sList.put(x, new ReplaceableStringList(conf.getStringList(x)));
            } else if (conf.isString(x)) {
                mr.sList.put(x, new ReplaceableStringList(Collections.singletonList(conf.getString(x))));
            }
        }
        return mr;
    }

    public static MessageReplacer register(JavaPlugin pl, File fx) {
        return register(pl.getClass(), fx);
    }

    public static MessageReplacer register(JavaPlugin pl) {
        return register(pl, new File(pl.getDataFolder(), "message.yml"));
    }

    public static MessageReplacer get(Class pl) {
        return replacers.get(pl);
    }

    public MessageReplacer register(Enum<?> targetText) {
        try {
            Method mtd = targetText.getDeclaringClass().getMethod("get");
            if (mtd.getReturnType().equals(String.class)) {
                String x = (String) mtd.invoke(targetText);
                register(targetText.name(), x);
            } else if (mtd.getReturnType().isAssignableFrom(List.class)) {
                List<String> x = (List<String>) mtd.invoke(targetText);
                register(targetText.name(), x.toArray(new String[0]));
            } else {
                throw new IllegalStateException("Enum class " + targetText.getDeclaringClass().getName() + " not contains method \"public String get()\" or \"public List<String> get\"");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Enum class " + targetText.getDeclaringClass().getName() + " not contains method \"public String get()\" or \"public List<String> get\"");
        }
        return this;
    }

    public MessageReplacer collapse(MessageReplacer mr) {
        for (String x : mr.sList.keySet()) {
            if (!sList.containsKey(x))
                sList.put(x, mr.sList.get(x).copy());
        }
        return this;
    }


    public MessageReplacer register(Enum[] vals) {
        for (Enum el : vals) {
            try {
                register(el);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    public MessageReplacer register(String name, String... txt) {
        sList.put(name, new ReplaceableStringList(new ArrayList<>(Arrays.asList(txt))));
        return this;
    }


    public MessageReplacer registerIfNotExists(Enum<?> targetText) {
        try {
//            System.out.println(sList);
            if (sList.containsKey(targetText.name()))
                return this;
            Method mtd = targetText.getDeclaringClass().getMethod("get");
            if (mtd.getReturnType().equals(String.class)) {
                String x = (String) mtd.invoke(targetText);
//                System.out.println("Registering" + x);
                register(targetText.name(), x);
            } else if (mtd.getReturnType().isAssignableFrom(List.class)) {
                List<String> x = (List<String>) mtd.invoke(targetText);
//                System.out.println("Registering" + x);
                register(targetText.name(), x.toArray(new String[0]));
            } else {
                throw new IllegalStateException("Enum class " + targetText.getDeclaringClass().getName() + " not contains method \"public String get()\" or \"public List<String> get\"");
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Enum class " + targetText.getDeclaringClass().getName() + " not contains method \"public String get()\" or \"public List<String> get\"");
        }
        return this;
    }

    public MessageReplacer registerIfNotExists(Enum[] vals) {
//        System.out.println("What?");
        for (Enum el : vals) {
            try {
                registerIfNotExists(el);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    public MessageReplacer registerIfNotExists(String name, String... txt) {
        if (sList.containsKey(name))
            return this;
        sList.put(name, new ReplaceableStringList(new ArrayList<>(Arrays.asList(txt))));
        return this;
    }

    public MessageReplacer copy() {
        MessageReplacer mr = new MessageReplacer();
        mr.sList = new HashMap<>(sList);
        return mr;
    }

    public ReplaceableStringList get(String name) {
//        System.out.println(sList);
        return sList.get(name) == null ? null : sList.get(name).copy();
    }

    public ReplaceableStringList get(Enum<?> enumVal) {
        return get(enumVal.name());
    }

    public void save(JavaPlugin pl) {
        save(new File(pl.getDataFolder(), "message.yml"));
    }

    public void save(File to) {
        YamlConfiguration conf = new YamlConfiguration();
        for (String x : sList.keySet()) {
            List<String> text = sList.get(x).getOriginal();
            conf.set(x, text.size() == 1 ? text.get(0) : text);
        }
        try {
            conf.save(to);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

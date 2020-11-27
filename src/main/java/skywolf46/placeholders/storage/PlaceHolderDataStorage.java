package skywolf46.placeholders.storage;

import java.util.HashMap;

public class PlaceHolderDataStorage {
    private static HashMap<Character, PlaceHolderDataStorage> storage = new HashMap<>();
    private HashMap<Character, PlaceHolderStorage> localStorage = new HashMap<>();

    public static PlaceHolderStorage getStorage(char start, char end) {
        return getStorage(start)
                .localStorage.computeIfAbsent(end, a -> new PlaceHolderStorage());
    }

    public PlaceHolderStorage getEndingStorage(char end) {
        return localStorage.computeIfAbsent(end, a -> new PlaceHolderStorage());
    }

    public static boolean isHolderStarter(char c) {
        return storage.containsKey(c);
    }

    public static PlaceHolderDataStorage getStorage(char start) {
        return storage.computeIfAbsent(start, a -> new PlaceHolderDataStorage());
    }

    public boolean isEndingCharacter(char c) {
        return localStorage.containsKey(c);
    }
}

package skywolf46.placeholders.storage;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;

import java.util.HashMap;

public class PlaceHolderStorage {
    private HashMap<String, AbstractPlaceHolder> holders = new HashMap<>();

    public AbstractPlaceHolder getPlaceHolder(String name) {
//        System.out.println("Holders: " + holders);
        return holders.get(name);
    }

    public void registerHolder(AbstractPlaceHolder holder) {
        holders.put(holder.getName(), holder);
    }
}

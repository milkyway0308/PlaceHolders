package skywolf46.placeholders.abstraction;

import skywolf46.placeholders.storage.PlaceHolderDataStorage;
import skywolf46.placeholders.util.ParameterStorage;

import java.util.HashMap;

public abstract class AbstractPlaceHolder {
    private static HashMap<String, AbstractPlaceHolder> storage = new HashMap<>();

    public abstract String getName();

    public abstract AbstractPlaceHolder parse(String[] parameters);

    public abstract String asString(ParameterStorage storage);

    public void register(char start, char end) {
        PlaceHolderDataStorage.getStorage(start, end).registerHolder(this);
    }

}

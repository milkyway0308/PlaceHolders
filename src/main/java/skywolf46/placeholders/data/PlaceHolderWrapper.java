package skywolf46.placeholders.data;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.ParameterStorage;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolderWrapper {
    private List<AbstractPlaceHolder> holders = new ArrayList<>();

    public PlaceHolderWrapper(List<AbstractPlaceHolder> hold) {
        this.holders = hold;
    }

    public String asString(ParameterStorage storage) {
        StringBuilder sb = new StringBuilder();
        for (AbstractPlaceHolder ah : holders)
            sb.append(ah.asString(storage));
        return sb.toString();
    }
}

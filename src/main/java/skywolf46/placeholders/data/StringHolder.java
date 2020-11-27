package skywolf46.placeholders.data;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.ParameterStorage;

public class StringHolder extends AbstractPlaceHolder {
    private String data;

    public StringHolder(String data) {
        this.data = data;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        return null;
    }

    @Override
    public String asString(ParameterStorage storage) {
        return this.data;
    }
}

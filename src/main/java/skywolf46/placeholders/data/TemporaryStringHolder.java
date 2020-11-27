package skywolf46.placeholders.data;

import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.ParameterStorage;

public class TemporaryStringHolder extends AbstractPlaceHolder {
    private String target;

    public TemporaryStringHolder(String parameter) {
        this.target = parameter;
//        System.out.println("Target: " + target);
    }

    @Override
    public String getName() {
        return "Temporary";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        return new TemporaryStringHolder(parameters[0]);
    }

    @Override
    public String asString(ParameterStorage storage) {
        return storage.get(target) == null ? "<" + target + ">" : storage.get(target);
    }
}

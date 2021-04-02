package skywolf46.placeholders.impl.holders.minecraft;

import org.bukkit.Bukkit;
import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.MessageParameters;

public class MaxPlayerHolder extends AbstractPlaceHolder {
    @Override
    public String getName() {
        return "maxPlayers";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        return new MaxPlayerHolder();
    }

    @Override
    public String asString(MessageParameters storage) {
        return String.valueOf(Bukkit.getMaxPlayers());
    }
}

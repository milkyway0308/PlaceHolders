package skywolf46.placeholders.impl.holders.minecraft;

import org.bukkit.Bukkit;
import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.MessageParameters;

public class CurrentPlayerHolder extends AbstractPlaceHolder {
    @Override
    public String getName() {
        return "currentPlayers";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        return new CurrentPlayerHolder();
    }

    @Override
    public String asString(MessageParameters storage) {
        return String.valueOf(Bukkit.getOnlinePlayers().size());
    }
}

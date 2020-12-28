package skywolf46.placeholders.impl.holders.minecraft;

import org.bukkit.entity.Player;
import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.util.MessageParameters;

public class PlayerHolder extends AbstractPlaceHolder {
    @Override
    public String getName() {
        return "player";
    }

    @Override
    public AbstractPlaceHolder parse(String[] parameters) {
        return new PlayerHolder();
    }

    @Override
    public String asString(MessageParameters storage) {
        Player px = storage.get(Player.class);
        return px == null ? "<player>" : px.getName();
    }
}

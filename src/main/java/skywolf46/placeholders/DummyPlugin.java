package skywolf46.placeholders;

import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.commandannotation.CommandAnnotation;
import skywolf46.placeholders.impl.holders.java.korean.PostWordPlaceHolder;
import skywolf46.placeholders.impl.holders.java.time.LocalTimePlaceHolder;
import skywolf46.placeholders.impl.holders.minecraft.CurrentPlayerHolder;
import skywolf46.placeholders.impl.holders.minecraft.MaxPlayerHolder;
import skywolf46.placeholders.impl.holders.minecraft.PlayerHolder;

public class DummyPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        PlaceHolders.registerHolder(new PlayerHolder(), '<', '>');
        PlaceHolders.registerHolder(new PostWordPlaceHolder(), '<', '>');
        PlaceHolders.registerHolder(new LocalTimePlaceHolder(null), '<', '>');
        PlaceHolders.registerHolder(new CurrentPlayerHolder(), '<', '>');
        PlaceHolders.registerHolder(new MaxPlayerHolder(), '<', '>');
    }

    @Override
    public void onEnable() {
        CommandAnnotation.init(this);
    }
}

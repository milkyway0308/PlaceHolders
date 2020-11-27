package skywolf46.placeholders;

import org.bukkit.plugin.java.JavaPlugin;
import skywolf46.CommandAnnotation.v1_4R1.CommandAnnotation;

public class DummyPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        CommandAnnotation.forceInit(this);
    }
}

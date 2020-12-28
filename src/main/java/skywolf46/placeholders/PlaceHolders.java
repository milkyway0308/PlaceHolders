package skywolf46.placeholders;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.exception.PlaceHolderUnclosedException;
import skywolf46.placeholders.parser.PlaceHolderParser;
import skywolf46.placeholders.storage.PlaceHolderDataStorage;
import skywolf46.placeholders.util.MessageParameters;

import java.util.List;

public class PlaceHolders {
    public static void registerHolder(AbstractPlaceHolder holder, char start, char end) {
        PlaceHolderDataStorage.getStorage(start, end).registerHolder(holder);
    }

    public static void processItem(ItemStack item, MessageParameters storage) throws PlaceHolderUnclosedException {
        if (item == null || !item.hasItemMeta())
            return;
        ItemMeta meta = item.getItemMeta();
        processItemMeta(meta, storage);
        item.setItemMeta(meta);
    }

    private static void processItemMeta(ItemMeta meta, MessageParameters storage) throws PlaceHolderUnclosedException {
        if (meta.hasDisplayName())
            meta.setDisplayName(PlaceHolderParser.parse(meta.getDisplayName()).asString(storage));
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, PlaceHolderParser.parse(lore.get(i)).asString(storage));
            }
        }
    }
}

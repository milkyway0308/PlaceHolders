package skywolf46.messagereplacer.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.placeholders.data.PlaceHolderWrapper;
import skywolf46.placeholders.exception.PlaceHolderUnclosedException;
import skywolf46.placeholders.parser.PlaceHolderParser;
import skywolf46.placeholders.util.ParameterStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplaceableItemStack {
    private PlaceHolderWrapper name;
    private List<PlaceHolderWrapper> lore = null;
    private ItemStackModifier modifier = null;
    private HashMap<Enchantment, Integer> enchantModifier = null;

    public ReplaceableItemStack(ConfigurationSection section) throws PlaceHolderUnclosedException {
        if (section.isString("Name")) {
            name = PlaceHolderParser.parse(section.getString("Name"));
        }

        if (section.isList("Lore")) {
            lore = new ArrayList<>();
            section.getStringList("Lore")
                    .forEach(str -> {
                        try {
                            lore.add(PlaceHolderParser.parse(str));
                        } catch (PlaceHolderUnclosedException e) {
                            e.printStackTrace();
                        }
                    });
        }
        if (section.isConfigurationSection("Data")) {
            section = section.getConfigurationSection("Data");

        }

    }

    public ItemStack build(ParameterStorage storage) {
        if (modifier == null) {
            throw new IllegalStateException("Error: Uncompleted item section; Modifier not exists");
        }
        ItemStack item = new ItemStack(modifier.material, 1, modifier.durability);
        modify(item, storage);
        return item;
    }

    public ItemStack modify(ItemStack item, ParameterStorage storage) {
        ItemMeta meta = item.getItemMeta();
        if (enchantModifier != null)
            for (Map.Entry<Enchantment, Integer> enchs : enchantModifier.entrySet())
                meta.addEnchant(enchs.getKey(), enchs.getValue(), false);
        if (name != null)
            meta.setDisplayName(name.asString(storage));
        if (lore != null) {
            List<String> ris = new ArrayList<>();
            for (PlaceHolderWrapper wr : lore)
                ris.add(wr.asString(storage));
            meta.setLore(ris);
        }
        item.setItemMeta(meta);
        return item;
    }

    class ItemStackModifier {
        private Material material;
        private short durability = 0;
    }

}

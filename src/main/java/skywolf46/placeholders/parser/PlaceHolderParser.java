package skywolf46.placeholders.parser;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import skywolf46.NBTUtil.v1_2R4.ItemNBTExtrator;
import skywolf46.NBTUtil.v1_2R4.ItemNBTImporter;
import skywolf46.NBTUtil.v1_2R4.NBTData.ReflectedNBTCompound;
import skywolf46.placeholders.abstraction.AbstractPlaceHolder;
import skywolf46.placeholders.data.PlaceHolderWrapper;
import skywolf46.placeholders.data.StringHolder;
import skywolf46.placeholders.data.TemporaryStringHolder;
import skywolf46.placeholders.exception.PlaceHolderUnclosedException;
import skywolf46.placeholders.storage.PlaceHolderDataStorage;
import skywolf46.placeholders.storage.PlaceHolderStorage;
import skywolf46.placeholders.util.ParameterStorage;

import java.util.ArrayList;
import java.util.List;

// Schema
// <Starting> Text:Parameter1:Parameter2.... <Ending>

public class PlaceHolderParser {

    public static ItemStack applyParser(ItemStack item) {
        if (!item.hasItemMeta())
            return item;
        ItemMeta meta = item.getItemMeta();
        ReflectedNBTCompound comp = ItemNBTExtrator.extractOrCreateNBT(item);
        if (meta.hasDisplayName())
            comp.set("BName", meta.getDisplayName());
        if (meta.hasLore())
            comp.set("BList", meta.getLore());
        return ItemNBTImporter.importNBT(item, comp);
    }

    public static ItemStack parse(ItemStack item, ParameterStorage storage) {
        storage.add(item);
        storage.add(ItemNBTExtrator.extractOrCreateNBT(item));
        if (!item.hasItemMeta())
            return item;
        ItemMeta meta = item.getItemMeta();
        ReflectedNBTCompound comp = ItemNBTExtrator.extractOrCreateNBT(item);
        if (comp.containsKey("BName")) {
            try {
                meta.setDisplayName(parse((String) comp.get("BName")).asString(storage));
            } catch (PlaceHolderUnclosedException e) {
                e.printStackTrace();
            }
        }

        if (comp.containsKey("BList")) {
            List<String> lr = (List<String>) comp.get("BList");
            for (int i = 0; i < lr.size(); i++) {
                try {
                    lr.set(i, parse(lr.get(i)).asString(storage));
                } catch (PlaceHolderUnclosedException e) {
                    e.printStackTrace();
                }
            }
            meta.setLore(lr);
        }
        item.setItemMeta(meta);
        return item;
    }

    public static PlaceHolderWrapper parse(String x) throws PlaceHolderUnclosedException {
        List<AbstractPlaceHolder> holders = new ArrayList<>();
        int lastParse = 0;
        for (int i = 0; i < x.length(); i++) {
            int current = i;
            char c = x.charAt(i);
            if (PlaceHolderDataStorage.isHolderStarter(c)) {
                PlaceHolderDataStorage storage = PlaceHolderDataStorage.getStorage(c);
                PlaceHolderStorage holderStorage = null;
                // Escape
                if (i != 0 && x.charAt(i - 1) == '\\')
                    continue;
                // Loop until end
                for (; current < x.length(); current++) {
                    if (storage.isEndingCharacter(x.charAt(current))) {
                        holderStorage = storage.getEndingStorage(x.charAt(current));
//                        System.out.println("Start..." + x.charAt(i));
//                        System.out.println("End..." + x.charAt(current));
                        break;
                    }
                }
                if (holderStorage == null) {
                    // No ending holder, add string item
//                    holders.add(new StringHolder(x.substring(i, current)));
//                    return holders;
//                     Not broken : scan next starting character
//                    continue;
                    // Throw exception for safe
                    continue;
                }
                // Add If string left
                if (i != lastParse) {
                    holders.add(new StringHolder(x.substring(lastParse, i)));
                }
//                if (holderStorage == null) {
//                    holders.add(new TemporaryStringHolder(x.substring(i + 1, current)));
//                    lastParse = current + 1;
//                    i = current + 1;
//                    continue;
//                }
                // Substring, and parse
                String parse = x.substring(i, current + 1);
                AbstractPlaceHolder holder = splitAndProcess(holderStorage, parse);
                holders.add(holder == null ? new StringHolder(parse) : holder);
                lastParse = current + 1;
                i = current + 1;
            }
        }
        if (lastParse != x.length()) {
            // Add last if not ended
            holders.add(new StringHolder(x.substring(lastParse)));
        }
        return new PlaceHolderWrapper(holders);
    }

    private static AbstractPlaceHolder splitAndProcess(PlaceHolderStorage storage, String toParse) {
        // TODO : Fix code to parse parameter
        toParse = toParse.substring(1, toParse.length() - 1);
        AbstractPlaceHolder ph = storage.getPlaceHolder(toParse);
        if (ph == null)
            return new TemporaryStringHolder(toParse);
        return ph.parse(new String[]{toParse});
    }

    // Test
    public static void main(String[] args) {
        PlaceHolderDataStorage.getStorage('<', '>').registerHolder(new TestHolder());
        StringBuilder sb = new StringBuilder();
        try {
            PlaceHolderWrapper holder = parse("별 하나에 버그 하나, 별 하나에 코드 하나, 별 하나에 어머니, 어머니... <테스트> < 버그 헤는 밤 > <테스트2>");
//            System.out.println("Holder length: " + holder.size());
//            for (AbstractPlaceHolder hold : holder)
//                sb.append(hold.toString());
            System.out.println("Result: " + holder.asString(ParameterStorage.of().add("테스트2", "<Tester>")));
        } catch (PlaceHolderUnclosedException e) {
            e.printStackTrace();
        }

    }

    private static class TestHolder extends AbstractPlaceHolder {
        @Override
        public String getName() {
            return "테스트";
        }

        @Override
        public AbstractPlaceHolder parse(String[] parameters) {
            return new TestHolder();
        }

        @Override
        public String asString(ParameterStorage storage) {
            return "<Test>";
        }
    }
}

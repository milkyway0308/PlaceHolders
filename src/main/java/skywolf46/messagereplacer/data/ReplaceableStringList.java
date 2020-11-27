package skywolf46.messagereplacer.data;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import skywolf46.placeholders.data.PlaceHolderWrapper;
import skywolf46.placeholders.exception.PlaceHolderUnclosedException;
import skywolf46.placeholders.parser.PlaceHolderParser;
import skywolf46.placeholders.util.ParameterStorage;

import java.util.ArrayList;
import java.util.List;

public class ReplaceableStringList {
    private List<PlaceHolderWrapper> wrappers = new ArrayList<>();
    private List<String> original;

    public ReplaceableStringList(List<String> xtr, boolean replace) {
        original = new ArrayList<>(xtr);
        for (String x : xtr) {
            try {
                wrappers.add(PlaceHolderParser.parse(replace ? ChatColor.translateAlternateColorCodes('&', x) : x));
            } catch (PlaceHolderUnclosedException e) {
                e.printStackTrace();
            }
        }
    }

    public ReplaceableStringList(List<String> xtr) {
        this(xtr, true);
    }

    private ReplaceableStringList() {

    }

    public List<String> parse(ParameterStorage ps) {
        List<String> txt = new ArrayList<>();
        for (PlaceHolderWrapper pw : wrappers)
            txt.add(pw.asString(ps));
        return txt;
    }

    public List<String> parse(Object... params) {
        return parse(ParameterStorage.of(params));
    }

    public String parseSingle(Object... params) {
        return parseSingle(ParameterStorage.of(params));
    }

    public String parseSingle(ParameterStorage storage) {
        return wrappers.size() == 0 ? null : wrappers.get(0).asString(storage);
    }

    public void sendTo(Player p) {
        sendTo(p, ParameterStorage.of(p));
    }

    public void sendTo(Player p, Object... o) {
        sendTo(p, ParameterStorage.of(o));
    }

    public void sendTo(Player p, ParameterStorage storage) {
        storage.add(p);
        for (PlaceHolderWrapper pw : wrappers)
            p.sendMessage(pw.asString(storage));
    }

    public List<String> getOriginal() {
        return new ArrayList<>(original);
    }

    public ReplaceableStringList copy() {
        ReplaceableStringList sl = new ReplaceableStringList();
        sl.wrappers = new ArrayList<>(sl.wrappers);
        sl.original = new ArrayList<>(original);
        return sl;

    }
}

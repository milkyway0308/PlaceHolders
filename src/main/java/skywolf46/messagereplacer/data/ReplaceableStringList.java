package skywolf46.messagereplacer.data;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import skywolf46.placeholders.data.PlaceHolderWrapper;
import skywolf46.placeholders.exception.PlaceHolderUnclosedException;
import skywolf46.placeholders.parser.PlaceHolderParser;
import skywolf46.placeholders.util.MessageParameters;

import java.util.ArrayList;
import java.util.List;

public class ReplaceableStringList {
    private List<PlaceHolderWrapper> wrappers = new ArrayList<>();
    private List<String> original;

    public ReplaceableStringList(List<String> xtr, boolean replace) {
        original = new ArrayList<>(xtr);
//        System.out.println(xtr);
        for (String x : xtr) {
            try {
                wrappers.add(PlaceHolderParser.parse(replace ? ChatColor.translateAlternateColorCodes('&', x) : x));
            } catch (PlaceHolderUnclosedException e) {
                e.printStackTrace();
            }
        }
//        System.out.println("Wrapper size: " + wrappers.size());
    }

    public ReplaceableStringList(List<String> xtr) {
        this(xtr, true);
    }

    private ReplaceableStringList() {

    }

    public List<String> parse(MessageParameters ps) {
        List<String> txt = new ArrayList<>();
        for (PlaceHolderWrapper pw : wrappers)
            txt.add(pw.asString(ps));
        return txt;
    }

    public List<String> parse(Object... params) {
        return parse(MessageParameters.of(params));
    }

    public String parseSingle(Object... params) {
        return parseSingle(MessageParameters.of(params));
    }

    public String parseSingle(MessageParameters storage) {
        return wrappers.size() == 0 ? null : wrappers.get(0).asString(storage);
    }

    public void sendParameterTo(Player p, MessageParameters storage) {
        sendTo(p, storage);
    }


    public void sendParameterTo(CommandSender p, MessageParameters storage) {
        sendTo(p, storage);
    }

    public void sendTo(Player p) {
        sendTo(p, MessageParameters.of(p));
    }

    public void sendTo(Player p, Object... o) {
        sendTo(p, MessageParameters.of(o));
    }


    public void sendTo(Player p, MessageParameters storage) {
        storage.add(p);
//        System.out.println("Wrapper " + wrappers.size());
        for (PlaceHolderWrapper pw : wrappers) {
//            System.out.println(pw.asString(storage));
            p.sendMessage(pw.asString(storage));
        }
    }

    public void sendTo(CommandSender p) {
        sendTo(p, MessageParameters.of(p));
    }

    public void sendTo(CommandSender p, Object... o) {
        sendTo(p, MessageParameters.of(o));
    }

    public void sendTo(CommandSender p, MessageParameters storage) {
        storage.add(p);
//        System.out.println("Wrapper " + wrappers.size());
        for (PlaceHolderWrapper pw : wrappers) {
//            System.out.println(pw.asString(storage));
            p.sendMessage(pw.asString(storage));
        }
    }


    public List<String> getOriginal() {
        return new ArrayList<>(original);
    }

    public ReplaceableStringList copy() {
        ReplaceableStringList sl = new ReplaceableStringList();
        sl.wrappers = new ArrayList<>(wrappers);
        sl.original = new ArrayList<>(original);
        return sl;

    }
}

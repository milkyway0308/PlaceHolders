package skywolf46.placeholders.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import skywolf46.CommandAnnotation.v1_4R1.Annotations.$_;
import skywolf46.placeholders.parser.PlaceHolderParser;
import skywolf46.placeholders.util.ParameterStorage;

public class PlaceHolderCommand {
    @$_("/phapply")
    public static void apply(Player p) {
        if (p == null)
            return;
        if (!p.hasPermission("ph.admin") && !p.isOp()) {
            p.sendMessage("§c권한이 부족합니다.");
            return;
        }
        ItemStack hand = p.getInventory().getItemInMainHand();
        if (hand == null || hand.getType() == Material.AIR) {
            p.sendMessage("§c아이템을 들고 사용해야 합니다.");
            return;
        }
        hand = PlaceHolderParser.applyParser(hand);
        PlaceHolderParser.parse(hand, ParameterStorage.of());
        p.getInventory().setItemInMainHand(hand);
        p.sendMessage("§a설정되었습니다.");
    }

}

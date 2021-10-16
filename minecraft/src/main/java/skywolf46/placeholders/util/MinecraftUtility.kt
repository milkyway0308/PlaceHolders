package skywolf46.placeholders.util

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.data.WrappedMessage

object MinecraftUtility {
    fun WrappedMessage.send(sender: CommandSender, vararg data: Any) {
        val storage = ArgumentStorage().apply {
            addArgument(sender)
            for (x in data)
                addArgument(x)
        }
        parseWith(storage).forEach {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', it))
        }
    }


    fun WrappedMessage.send(sender: CommandSender, vararg parameter: Pair<String, Any>) {
        val storage = ArgumentStorage().apply {
            addArgument(sender)
            for (x in parameter)
                set(x.first, x.second)
        }
        parseWith(storage).forEach {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', it))
        }
    }

    fun WrappedMessage.send(sender: CommandSender) {
        val storage = ArgumentStorage().apply {
            addArgument(sender)
        }
        parseWith(storage).forEach {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', it))
        }
    }
}
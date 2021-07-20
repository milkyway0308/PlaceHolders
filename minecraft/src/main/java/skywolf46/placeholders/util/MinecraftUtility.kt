package skywolf46.placeholders.util

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
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
            sender.sendMessage(it)
        }
    }


    fun WrappedMessage.send(sender: CommandSender, vararg parameter: Pair<String, Any>) {
        val storage = ArgumentStorage().apply {
            addArgument(sender)
            for (x in parameter)
                set(x.first, x.second)
        }
        parseWith(storage).forEach {
            sender.sendMessage(it)
        }
    }
}
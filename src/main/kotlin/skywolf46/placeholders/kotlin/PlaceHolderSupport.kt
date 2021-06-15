package skywolf46.placeholders.kotlin

import org.bukkit.plugin.java.JavaPlugin
import skywolf46.messagereplacer.MessageReplacer
import java.io.File

class PlaceHolderSupport {
}

fun <T> T.storage() = MessageReplacer.get(this!!::class.java)

fun <T> T.registerStorage(fx: File) = MessageReplacer.register(this!!::class.java, fx)

inline fun <reified T : Enum<Any>> T.toMessage() = MessageReplacer.get(T::class.java)[this]

fun <T : Enum<Any>> Class<out T>.registerAll(plugin: JavaPlugin) {
    registerAll(File(plugin.dataFolder, "messages.yml"))
}


fun <T : Enum<Any>> Class<out T>.registerAll(file: File) {
    MessageReplacer.register(this, file).registerIfNotExists(getMethod("values").invoke(null) as Array<Enum<*>>)
        .save(file)
}
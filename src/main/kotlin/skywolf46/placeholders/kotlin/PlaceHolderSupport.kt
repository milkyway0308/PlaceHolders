package skywolf46.placeholders.kotlin

import org.bukkit.plugin.java.JavaPlugin
import skywolf46.messagereplacer.MessageReplacer
import java.io.File
import kotlin.reflect.KClass

class PlaceHolderSupport {
}

fun <T> T.storage() = MessageReplacer.get(this!!::class.java)

fun <T> T.registerStorage(fx: File) = MessageReplacer.register(this!!::class.java, fx)

inline fun <reified T : Enum<Any>> T.toMessage() = MessageReplacer.get(T::class.java)[this]

fun KClass<*>.registerIfEnum(plugin: JavaPlugin) {
    java.registerIfEnum(plugin)
}

fun KClass<*>.registerIfEnum(file: File) {
    java.registerIfEnum(file)
}

fun Class<*>.registerIfEnum(plugin: JavaPlugin) {
    registerIfEnum(File(plugin.dataFolder, "messages.yml"))
}


fun Class<*>.registerIfEnum(file: File) {
    if (Enum::class.java.isAssignableFrom(this))
        MessageReplacer.register(this, file).registerIfNotExists(getMethod("values").invoke(null) as Array<Enum<*>>)
            .save(file)
}
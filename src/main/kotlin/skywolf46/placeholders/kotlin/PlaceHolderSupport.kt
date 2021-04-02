package skywolf46.placeholders.kotlin

import skywolf46.messagereplacer.MessageReplacer
import java.io.File

class PlaceHolderSupport {
}

fun <T> T.storage() = MessageReplacer.get(this!!::class.java)

fun <T> T.registerStorage(fx: File) = MessageReplacer.register(this!!::class.java, fx)

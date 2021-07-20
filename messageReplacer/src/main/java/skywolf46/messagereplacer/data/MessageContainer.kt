package skywolf46.messagereplacer.data

import skywolf46.placeholderskotlin.data.WrappedString
import java.util.concurrent.atomic.AtomicLong

object MessageContainer {
    private val messages = mutableMapOf<String, List<WrappedString>>()
    private val messageIDCache = mutableMapOf<Pair<String, String>, String>()
    private val currentType = AtomicLong(0)

    fun nextID(cls: Class<*>, name: String): String {
        val key = cls.name to name
        if (key !in messageIDCache)
            messageIDCache[key] = currentType.incrementAndGet().toString()
        return messageIDCache[key]!!
    }

    fun updateMessage(id: String, message: List<WrappedString>) {
        messages[id] = ArrayList(message)
    }

    fun getMessage(id: String): List<WrappedString> {
        return messages[id] ?: ArrayList()
    }
}
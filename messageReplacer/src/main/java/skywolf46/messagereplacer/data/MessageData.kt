package skywolf46.messagereplacer.data

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.messagereplacer.util.MassiveYaml
import skywolf46.placeholderskotlin.PlaceHolders.compile
import skywolf46.placeholderskotlin.data.WrappedString

class MessageData(private val cls: Class<*>, val id: String) {
    companion object {
        private val SHARED_STORAGE = ArgumentStorage()
    }

    // Contains ID
    val languages = mutableMapOf<String, String>()

    internal operator fun set(lang: String, dataID: String) {
        languages[lang] = dataID
    }

    operator fun get(lang: String): List<WrappedString> {
        return MessageContainer.getMessage(getID(lang))
    }

    fun getID(lang: String): String {
        return languages.computeIfAbsent(lang) {
            "${MessageContainer.nextID(cls, id)}_$lang"
        }
    }

    fun getRawID(): String {
        return MessageContainer.nextID(cls, id)
    }

    fun update(lang: String, content: Iterable<String>) {
        MessageContainer.updateMessage(getID(lang), content.map { x -> x.compile(SHARED_STORAGE) })
    }

    fun save(saveString: String, yaml: MassiveYaml) {
        for ((x, _) in languages) {
            yaml[x].set(saveString, get(x).map { str -> str.rawString }.run {
                if (size == 1)
                    return@run this[0]
                else
                    this
            })
        }
    }

    fun collapse(data: MessageData) {
        languages.putAll(data.languages)
    }

}
package skywolf46.messagereplacer.util

import skywolf46.messagereplacer.MessageReplacer
import skywolf46.messagereplacer.data.MessageContainer
import skywolf46.placeholderskotlin.data.WrappedString

object MessageReplacerUtility {
    @JvmOverloads
    @JvmStatic
    fun Array<String>.i18n(locale: String = MessageReplacer.DEFAULT_LANGUAGE): List<WrappedString> {
        return MessageContainer.getMessage("${this[0]}_$locale")
    }

    @JvmOverloads
    @JvmStatic
    fun List<String>.i18n(locale: String = MessageReplacer.DEFAULT_LANGUAGE): List<WrappedString> {
        return MessageContainer.getMessage("${this[0]}_$locale")
    }

    @JvmOverloads
    @JvmStatic
    fun String.i18n(locale: String = MessageReplacer.DEFAULT_LANGUAGE): List<WrappedString> {
        return MessageContainer.getMessage("${this}_$locale")
    }
}
package skywolf46.placeholderskotlin.impl.placeholders

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

/**
 * Pure string placeholder.
 * This class used to append immutable content.
 *
 * Cannot registered to placeholder storage.
 */
class PureStringHolder(val str: String) : AbstractPlaceHolder() {
    init {
        println("Pure string detected: $str")
    }

    override fun process(data: ArgumentData, inputParam: ArgumentStorage): String? {
        return str
    }

    override fun toString(): String {
        return "PureString(text=$str)"
    }
}
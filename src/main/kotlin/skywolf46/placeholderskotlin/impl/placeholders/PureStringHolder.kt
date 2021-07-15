package skywolf46.placeholderskotlin.impl.placeholders

import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

/**
 * Pure string placeholder.
 * This class used to append immutable content.
 *
 * Cannot registered to placeholder storage.
 */
class PureStringHolder(data: ArgumentData) : AbstractPlaceHolder(data) {
    override fun process(inputParam: ArgumentData): String? {
        return data.content
    }

}
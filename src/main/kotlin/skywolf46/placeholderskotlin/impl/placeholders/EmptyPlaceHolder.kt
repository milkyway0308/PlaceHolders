package skywolf46.placeholderskotlin.impl.placeholders

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

/**
 * Empty placeholder holder.
 * This class used to hold empty placeholder to replaced by interceptor([IAnalyzeBroker]].
 *
 * Cannot registered to placeholder storage.
 */
class EmptyPlaceHolder(val data: ArgumentData) : AbstractPlaceHolder() {

    override fun process(data: ArgumentData, inputParam: ArgumentStorage): String {
        return this.data.original
    }

    override fun toString(): String {
        return "EmptyPlaceHolder(text=${data.original})"
    }

}
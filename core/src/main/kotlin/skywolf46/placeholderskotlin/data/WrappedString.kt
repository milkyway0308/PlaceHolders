package skywolf46.placeholderskotlin.data

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder

open class WrappedString(val holders: List<Pair<ArgumentData, AbstractPlaceHolder>>) {
    open fun parse(storage: ArgumentStorage): String {
        val builder = StringBuilder()
        for (x in holders) {
            builder.append(x.second.process(x.first, storage))
        }
        return builder.toString()
    }

    override fun toString(): String {
        return "WrappedString(holders=$holders)"
    }


}
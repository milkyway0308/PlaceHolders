package skywolf46.placeholderskotlin.impl.placeholders

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

class SelfReplacingPlaceHolder : AbstractPlaceHolder() {
    override fun process(data: ArgumentData, inputParam: ArgumentStorage): String? {
        return inputParam[data.content] ?: data.original
    }

    override fun toString(): String {
        return "SelfReplacingHolder()"
    }
}
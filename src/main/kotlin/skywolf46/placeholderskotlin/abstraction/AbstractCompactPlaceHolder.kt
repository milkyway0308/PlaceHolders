package skywolf46.placeholderskotlin.abstraction

import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.data.ArgumentData

abstract class AbstractCompactPlaceHolder : AbstractPlaceHolder() {
    final override fun process(data: ArgumentData, inputParam: ArgumentStorage): String? {
        return process(Arguments(false, "", inputParam, data.parameters.toTypedArray(), 0))
    }

    abstract fun process(data: Arguments) : String?
}
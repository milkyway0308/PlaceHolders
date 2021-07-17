package skywolf46.placeholderkotlin.test.impl

import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractCompactPlaceHolder
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

class TestParameterHolder : AbstractCompactPlaceHolder() {
    override fun process(data: Arguments): String? {
        data.arg<Int>(false) {
            return "0x${it.toString(8)}"
        }
        return null
    }
}
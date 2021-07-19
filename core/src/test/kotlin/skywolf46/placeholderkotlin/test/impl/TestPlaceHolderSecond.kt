package skywolf46.placeholderkotlin.test.impl

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData

class TestPlaceHolderSecond : AbstractPlaceHolder(){
    override fun process(data: ArgumentData, inputParam: ArgumentStorage): String? {
        return "Test2"
    }
}
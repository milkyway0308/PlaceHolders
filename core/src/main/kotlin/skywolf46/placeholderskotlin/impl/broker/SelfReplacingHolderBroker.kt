package skywolf46.placeholderskotlin.impl.broker

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolderBroker
import skywolf46.placeholderskotlin.enums.AnalyzeProgress
import skywolf46.placeholderskotlin.impl.placeholders.EmptyPlaceHolder
import skywolf46.placeholderskotlin.impl.placeholders.PureStringHolder
import skywolf46.placeholderskotlin.impl.placeholders.SelfReplacingPlaceHolder

object SelfReplacingHolderBroker : AbstractPlaceHolderBroker() {
    override fun intercept(
        step: AnalyzeProgress,
        storage: ArgumentStorage,
        input: String,
        derived: AbstractPlaceHolder,
    ): AbstractPlaceHolder? {
        if (derived is EmptyPlaceHolder) {
//            storage.get<Any>(derived.data.content)?.toString()?.apply {
//                println("...Replacing ${derived.data.content} with temp holder.")
//            }
            return SelfReplacingPlaceHolder()
        }
        return null
    }
}
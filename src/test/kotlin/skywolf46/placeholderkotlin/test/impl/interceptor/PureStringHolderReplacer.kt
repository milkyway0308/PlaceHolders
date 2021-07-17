package skywolf46.placeholderkotlin.test.impl.interceptor

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractAnalyzeBroker
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolderBroker
import skywolf46.placeholderskotlin.data.WrappedString
import skywolf46.placeholderskotlin.enums.AnalyzeProgress
import skywolf46.placeholderskotlin.impl.placeholders.EmptyPlaceHolder
import skywolf46.placeholderskotlin.impl.placeholders.PureStringHolder

object PureStringHolderReplacer : AbstractPlaceHolderBroker() {
    override fun intercept(
        step: AnalyzeProgress,
        storage: ArgumentStorage,
        input: String,
        derived: AbstractPlaceHolder,
    ): AbstractPlaceHolder? {
        if (derived is EmptyPlaceHolder) {
            storage.get<Any>(derived.data.content)?.toString()?.apply {
                return PureStringHolder(this)
            }
        }
        return null
    }
}
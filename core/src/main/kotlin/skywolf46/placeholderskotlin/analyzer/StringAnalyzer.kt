package skywolf46.placeholderskotlin.analyzer

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractAnalyzer
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData
import skywolf46.placeholderskotlin.data.PlaceHolderPrepare
import skywolf46.placeholderskotlin.data.WrappedString
import skywolf46.placeholderskotlin.enums.AnalyzeProgress
import skywolf46.placeholderskotlin.impl.placeholders.EmptyPlaceHolder
import skywolf46.placeholderskotlin.impl.placeholders.PureStringHolder
import skywolf46.placeholderskotlin.storage.CharacterInspector
import skywolf46.placeholderskotlin.storage.PlaceHolderManager
import skywolf46.placeholderskotlin.storage.PlaceHolderSuffixInspector
import skywolf46.placeholderskotlin.util.clearAndGet

class StringAnalyzer(val manager: PlaceHolderManager) :
    AbstractAnalyzer<String, AbstractPlaceHolder, WrappedString, StringAnalyzer>() {
    companion object {
        private val EMPTY_DATA = ArgumentData("", "", "", "", mutableListOf())
    }

    override fun analyze(broker: WrappedBrokers, data: ArgumentStorage, target: String): WrappedString {
        val list = mutableListOf<Pair<ArgumentData, AbstractPlaceHolder>>()
        var index = 0
        val emptyHolder = StringBuilder()
        while (index < target.length) {
            with(checkState(index, target, data)) {
                second?.apply {
                    if (emptyHolder.isNotEmpty()) {
                        list += EMPTY_DATA to PureStringHolder(emptyHolder.clearAndGet())
                    }
                    index = analyzeState(broker, data, list, this@with)
                } ?: kotlin.run {
                    emptyHolder.append(target[index++])
                }
            }
        }
        if (emptyHolder.isNotEmpty()) {
            list += EMPTY_DATA to PureStringHolder(emptyHolder.clearAndGet())
        }
        return WrappedString(target, list)
    }

    private fun analyzeState(
        broker: WrappedBrokers,
        data: ArgumentStorage,
        list: MutableList<Pair<ArgumentData, AbstractPlaceHolder>>,
        parsed: Pair<Int, ArgumentData?>,
    ): Int {
        parsed.second?.apply {
            val result = (broker.intercept(AnalyzeProgress.ANALYZING, data, content)
                ?: this.toPlaceHolder(manager)
                ?: EmptyPlaceHolder(this))
                .run {
                    broker.intercept(AnalyzeProgress.ANALYZED, data, content, this) ?: this
                }
            list.add(this to result)
            broker.probeStep(AnalyzeProgress.ANALYZED, data, list[list.size - 1].second)
        }
        return parsed.first
    }

    private fun checkState(index: Int, target: String, storage: ArgumentStorage): Pair<Int, ArgumentData?> {
        val inspector = manager.getPrefixInspector()
        inspector.checkMaximumAcceptableValue(index, target)?.let { data ->
            inspector.getValue(data.second)?.findMatchingSuffix(data.first, target)?.apply {
                return first to PlaceHolderAnalyzer.analyze(arrayOf(),
                    storage,
                    PlaceHolderPrepare(data.second,
                        second,
                        target.substring(data.first, first - second.length)))
            }
        }
        return index to null
    }


    private fun PlaceHolderSuffixInspector.findMatchingSuffix(index: Int, content: String): Pair<Int, String>? {
        for (x in index until content.length) {
            checkMaximumAcceptableValue(x, content)?.apply {
                return this
            }
        }
        return null
    }

    private fun <X : Any> CharacterInspector<X>.checkMaximumAcceptableValue(
        startIndex: Int,
        content: String,
    ): Pair<Int, String>? {

        for (x in startIndex + 1 until content.length) {
            if (isAcceptable(content.substring(startIndex, x))) {
                if (x == startIndex)
                    return null
                return x to content.substring(startIndex, x)
            }
        }
        return null
    }


}
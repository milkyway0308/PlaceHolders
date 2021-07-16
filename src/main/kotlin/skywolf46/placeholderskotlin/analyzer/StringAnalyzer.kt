package skywolf46.placeholderskotlin.analyzer

import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.extrautility.util.test
import skywolf46.placeholderskotlin.abstraction.AbstractAnalyzer
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.data.ArgumentData
import skywolf46.placeholderskotlin.data.PlaceHolderPrepare
import skywolf46.placeholderskotlin.data.WrappedString
import skywolf46.placeholderskotlin.enums.AnalyzeProgress
import skywolf46.placeholderskotlin.impl.placeholders.PureStringHolder
import skywolf46.placeholderskotlin.storage.CharacterInspector
import skywolf46.placeholderskotlin.storage.PlaceHolderManager
import skywolf46.placeholderskotlin.storage.PlaceHolderSuffixInspector
import skywolf46.placeholderskotlin.util.clearAndGet
import java.util.*

class StringAnalyzer(val manager: PlaceHolderManager) :
    AbstractAnalyzer<String, AbstractPlaceHolder, WrappedString, StringAnalyzer>() {
    companion object {
        private val EMPTY_DATA = ArgumentData("", "", "", "", ArgumentStorage())
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
                    println("Failing to parse, appending ${target[index]}")
                    emptyHolder.append(target[index++])
                }
            }
        }
        if (emptyHolder.isNotEmpty()) {
            list += EMPTY_DATA to PureStringHolder(emptyHolder.clearAndGet())
        }
        return WrappedString(list)
    }

    private fun analyzeState(
        broker: WrappedBrokers,
        data: ArgumentStorage,
        list: MutableList<Pair<ArgumentData, AbstractPlaceHolder>>,
        parsed: Pair<Int, ArgumentData?>,
    ): Int {
        parsed.second?.apply {
            broker.intercept(AnalyzeProgress.ANALYZING, data, content)
            list.add(this to (this.toPlaceHolder(manager) ?: PureStringHolder(original)))
            broker.probeStep(AnalyzeProgress.ANALYZED, data, list[list.size - 1].second)
        }
        return parsed.first
    }

    private fun checkState(index: Int, target: String, storage: ArgumentStorage): Pair<Int, ArgumentData?> {
        val inspector = manager.getPrefixInspector()
        inspector.checkMaximumAcceptableValue(index, target)?.let { data ->
            println("Matched ${data.second} (Current ${target.substring(0, data.first)})")
            inspector.getValue(data.second)?.findMatchingSuffix(data.first, target)?.apply {
                println("Index: ${index}, Result: ${this}")
                println("Pure: ${target.substring(index, first)}")
                println("Total: ${target.substring(0, first)}")
                println("Prestart ${data.first} -> PreEnd ${first}")
                println("Start ${data.first + data.second.length} -> End ${first - second.length}")
                println("Current: ${target.substring(data.first, first - second.length)}")
                return first to PlaceHolderAnalyzer.analyze(arrayOf(),
                    storage,
                    PlaceHolderPrepare(data.second,
                        second,
                        target.substring(data.first, first - second.length)))
            }
        }
        println("Failing.")
        return index to null
    }


    private fun PlaceHolderSuffixInspector.findMatchingSuffix(index: Int, content: String): Pair<Int, String>? {
        test()
        println("Finding")
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
                println("Accepted on ${startIndex}, Start index ${x}")
                return x to content.substring(startIndex, x)
            }
        }
        return null
    }


}
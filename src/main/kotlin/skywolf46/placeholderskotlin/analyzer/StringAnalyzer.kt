package skywolf46.placeholderskotlin.analyzer

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.IAnalyzeBroker
import skywolf46.placeholderskotlin.abstraction.IAnalyzer
import skywolf46.placeholderskotlin.data.WrappedString

object StringAnalyzer : IAnalyzer<String, WrappedString, StringAnalyzer> {
    override fun analyze(
        brokers: Array<IAnalyzeBroker<String, WrappedString, StringAnalyzer>>,
        data: ArgumentStorage,
        target: String,
    ): WrappedString {
        TODO("Not yet implemented")
    }

    override fun addBroker(broker: IAnalyzeBroker<String, WrappedString, StringAnalyzer>): StringAnalyzer {
        TODO("Not yet implemented")
    }
}
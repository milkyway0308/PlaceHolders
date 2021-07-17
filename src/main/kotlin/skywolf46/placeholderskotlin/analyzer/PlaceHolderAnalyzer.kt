package skywolf46.placeholderskotlin.analyzer

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.IAnalyzeBroker
import skywolf46.placeholderskotlin.abstraction.IAnalyzer
import skywolf46.placeholderskotlin.data.ArgumentData
import skywolf46.placeholderskotlin.data.PlaceHolderPrepare

object PlaceHolderAnalyzer : IAnalyzer<PlaceHolderPrepare, Nothing, ArgumentData, PlaceHolderAnalyzer> {
    override fun analyze(
        brokers: Array<IAnalyzeBroker<PlaceHolderPrepare, Nothing, ArgumentData, PlaceHolderAnalyzer>>,
        data: ArgumentStorage,
        target: PlaceHolderPrepare,
    ): ArgumentData {
        // TODO separate parameter
        val parameter = ArgumentStorage()
        return ArgumentData("${target.prefix}${target.content}${target.suffix}",
            target.prefix,
            target.suffix,
            target.content,
            parameter)
    }

    override fun addBroker(broker: IAnalyzeBroker<PlaceHolderPrepare, Nothing, ArgumentData, PlaceHolderAnalyzer>): PlaceHolderAnalyzer {
        throw IllegalStateException("Not supported")
    }

}
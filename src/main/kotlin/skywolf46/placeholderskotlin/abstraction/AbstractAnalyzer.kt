package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.enums.AnalyzeProgress

abstract class AbstractAnalyzer<INPUT : Any, RESULT : Any, SELF : IAnalyzer<INPUT, RESULT, SELF>> :
    IAnalyzer<INPUT, RESULT, SELF> {
    protected val brokers = mutableListOf<IAnalyzeBroker<INPUT, RESULT, SELF>>()

    abstract fun analyze(broker: WrappedBrokers, data: ArgumentStorage, target: INPUT): RESULT

    override fun analyze(
        brokers: Array<IAnalyzeBroker<INPUT, RESULT, SELF>>,
        data: ArgumentStorage,
        target: INPUT,
    ): RESULT {
        val wrappedBrokers = WrappedBrokers(brokers)
        wrappedBrokers.intercept(AnalyzeProgress.READY, data, target)
        return analyze(wrappedBrokers, data, target).apply {
            wrappedBrokers.probe(AnalyzeProgress.COMPLETE, data, this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun addBroker(broker: IAnalyzeBroker<INPUT, RESULT, SELF>): SELF {
        brokers += broker
        return this as SELF
    }

    inner class WrappedBrokers(temporaryBrokers: Array<IAnalyzeBroker<INPUT, RESULT, SELF>>) {
        val wrappedBrokers = mutableListOf<IAnalyzeBroker<INPUT, RESULT, SELF>>()

        init {
            this.wrappedBrokers.addAll(brokers)
            this.wrappedBrokers.addAll(temporaryBrokers)

        }

        fun intercept(progress: AnalyzeProgress, storage: ArgumentStorage, input: INPUT) {
            for (x in wrappedBrokers) {
                x.intercept(progress, storage, input)
            }
        }


        fun probe(progress: AnalyzeProgress, storage: ArgumentStorage, derive: RESULT) {
            for (x in wrappedBrokers) {
                x.probe(progress, storage, derive)
            }
        }
    }
}
package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.enums.AnalyzeProgress

abstract class AbstractAnalyzer<INPUT : Any, STEP : Any, RESULT : Any, SELF : IAnalyzer<INPUT, STEP, RESULT, SELF>> :
    IAnalyzer<INPUT, STEP, RESULT, SELF> {
    protected val brokers = mutableListOf<IAnalyzeBroker<INPUT, STEP, RESULT, SELF>>()

    abstract fun analyze(broker: WrappedBrokers, data: ArgumentStorage, target: INPUT): RESULT

    override fun analyze(
        brokers: Array<IAnalyzeBroker<INPUT, STEP, RESULT, SELF>>,
        data: ArgumentStorage,
        target: INPUT,
    ): RESULT {
        val wrappedBrokers = WrappedBrokers(brokers)
        wrappedBrokers.intercept(AnalyzeProgress.READY, data, target)
        return analyze(wrappedBrokers, data, target).apply {
            wrappedBrokers.probeResult(AnalyzeProgress.COMPLETE, data, this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun addBroker(broker: IAnalyzeBroker<INPUT, STEP, RESULT, SELF>): SELF {
        brokers += broker
        return this as SELF
    }

    inner class WrappedBrokers(temporaryBrokers: Array<IAnalyzeBroker<INPUT, STEP, RESULT, SELF>>) {
        val wrappedBrokers = mutableListOf<IAnalyzeBroker<INPUT, STEP, RESULT, SELF>>()

        init {
            this.wrappedBrokers.addAll(brokers)
            this.wrappedBrokers.addAll(temporaryBrokers)

        }

        fun intercept(progress: AnalyzeProgress, storage: ArgumentStorage, input: INPUT): STEP? {
            for (x in wrappedBrokers) {
                x.intercept(progress, storage, input)?.apply {
                    return this
                }
            }
            return null
        }


        fun intercept(progress: AnalyzeProgress, storage: ArgumentStorage, input: INPUT, derive: STEP): STEP? {
            for (x in wrappedBrokers) {
                x.intercept(progress, storage, input, derive)?.apply {
                    return this
                }
            }
            return null
        }


        fun probeStep(progress: AnalyzeProgress, storage: ArgumentStorage, derive: STEP) {
            for (x in wrappedBrokers) {
                x.probeStep(progress, storage, derive)
            }
        }


        fun probeResult(progress: AnalyzeProgress, storage: ArgumentStorage, derive: RESULT) {
            for (x in wrappedBrokers) {
                x.probeResult(progress, storage, derive)
            }
        }
    }
}
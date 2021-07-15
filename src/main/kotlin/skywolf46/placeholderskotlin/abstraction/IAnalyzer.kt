package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage

interface IAnalyzer<TARGET : Any, RESULT : Any, SELF : IAnalyzer<TARGET, RESULT, SELF>> {
    fun analyze(
        brokers: Array<IAnalyzeBroker<TARGET, RESULT, SELF>> = arrayOf(),
        data: ArgumentStorage,
        target: TARGET,
    ): RESULT

    fun analyzeAll(
        brokers: Array<IAnalyzeBroker<TARGET, RESULT, SELF>> = arrayOf(),
        storage: ArgumentStorage,
        vararg target: TARGET,
    ): List<RESULT> {
        return target.map { x -> analyze(brokers, storage, x) }
    }

    fun addBroker(broker: IAnalyzeBroker<TARGET, RESULT, SELF>): SELF
}
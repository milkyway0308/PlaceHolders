package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.enums.AnalyzeProgress

interface IAnalyzeBroker<INPUT : Any, RESULT : Any, TARGET : IAnalyzer<INPUT, RESULT, TARGET>> {

    fun intercept(step: AnalyzeProgress, storage: ArgumentStorage, input: INPUT)

    fun probe(step: AnalyzeProgress, storage: ArgumentStorage, derived: RESULT)

}
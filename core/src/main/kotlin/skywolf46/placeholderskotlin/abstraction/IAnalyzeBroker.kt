package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.enums.AnalyzeProgress

interface IAnalyzeBroker<INPUT : Any, STEP : Any, RESULT : Any, TARGET : IAnalyzer<INPUT, STEP, RESULT, TARGET>> {

    fun intercept(step: AnalyzeProgress, storage: ArgumentStorage, input: INPUT): STEP?

    fun intercept(step: AnalyzeProgress, storage: ArgumentStorage, input: INPUT, derived: STEP): STEP?

    fun probeStep(step: AnalyzeProgress, storage: ArgumentStorage, derived: STEP)

    fun probeResult(step: AnalyzeProgress, storage: ArgumentStorage, derived: RESULT)

}
package skywolf46.placeholderskotlin.abstraction

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.enums.AnalyzeProgress

abstract class AbstractAnalyzeBroker<INPUT : Any, STEP : Any, RESULT : Any, TARGET : IAnalyzer<INPUT, STEP, RESULT, TARGET>> :
    IAnalyzeBroker<INPUT, STEP, RESULT, TARGET> {
    override fun intercept(step: AnalyzeProgress, storage: ArgumentStorage, input: INPUT): STEP? {
        return null
    }

    override fun intercept(step: AnalyzeProgress, storage: ArgumentStorage, input: INPUT, derived: STEP): STEP? {
        return null
    }

    override fun probeStep(step: AnalyzeProgress, storage: ArgumentStorage, derived: STEP) {
        // Nothing here
    }

    override fun probeResult(step: AnalyzeProgress, storage: ArgumentStorage, derived: RESULT) {
        // Nothing here
    }
}
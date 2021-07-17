package skywolf46.placeholderkotlin.test.data

import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.analyzer.StringAnalyzer
import skywolf46.placeholderskotlin.storage.PlaceHolderManager

data class TestTarget(
    val target: String,
    val holderManager: PlaceHolderManager = PlaceHolderManager(),
    val analyzer: StringAnalyzer = StringAnalyzer(holderManager),
)

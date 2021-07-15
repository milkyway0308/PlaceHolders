package skywolf46.placeholderskotlin.data

import skywolf46.commandannotation.kotlin.data.Arguments

data class ArgumentData(
    val original: String,
    val prefix: String,
    val suffix: String,
    val content: String,
    val parameters: Arguments,
)
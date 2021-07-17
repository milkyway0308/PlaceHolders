package skywolf46.placeholderskotlin.util

private const val DELEMETER = ":"
private val QUOTED_REGEX = "$DELEMETER(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)".toRegex()

fun String.splitWithoutQuoted() : List<String> {
    return split(QUOTED_REGEX)
}
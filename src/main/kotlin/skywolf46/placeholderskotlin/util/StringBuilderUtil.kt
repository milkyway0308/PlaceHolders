package skywolf46.placeholderskotlin.util

fun StringBuilder.clearAndGet(): String {
    return toString().apply {
        clear()
    }
}
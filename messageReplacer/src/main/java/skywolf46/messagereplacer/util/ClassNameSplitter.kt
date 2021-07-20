package skywolf46.messagereplacer.util

object ClassNameSplitter {
    fun Class<*>.toProject(): String {
        return `package`.name.split(".").run {
            if (size <= 1)
                return@run this[0]
            return@run "${this[0]}.${this[1]}"
        }
    }
}
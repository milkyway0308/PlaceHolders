package skywolf46.placeholderskotlin.data

import skywolf46.extrautility.data.ArgumentStorage

class WrappedMessage(private val content: List<WrappedString>) {
    fun parseWith(storage: ArgumentStorage) = content.map { x -> x.parse(storage) }

    fun parse(vararg data: Any) = ArgumentStorage().run {
        for (x in data)
            addArgument(x)
        parseWith(this)
    }

    fun parseSingleWith(storage: ArgumentStorage) = if (content.isEmpty()) "" else content[0].parse(storage)

    fun parseSingle(vararg data: Any) = ArgumentStorage().run {
        for (x in data)
            addArgument(x)
        parseSingleWith(this)
    }
}
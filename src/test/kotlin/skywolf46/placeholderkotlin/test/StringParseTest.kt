package skywolf46.placeholderkotlin.test

import org.junit.Assert
import org.junit.jupiter.api.Test
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolder
import skywolf46.placeholderskotlin.PlaceHoldersKotlin
import skywolf46.placeholderskotlin.analyzer.StringAnalyzer

class StringParseTest {
    val storage = PlaceHoldersKotlin.DEFAULT_STORAGE
    val parser = StringAnalyzer(storage)

    @Test
    fun placeHolderFindTest() {
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolder::class.java)
        println(storage.findHolderStorage("<", ">")?.get("test"))
    }

    @Test
    fun placeHolderParseTest() {
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolder::class.java)
        println(parser.analyze(arrayOf(), ArgumentStorage(), "test, <test>, asdf, <test> <test> <Test>.").apply {
            println(parse(ArgumentStorage()))
        })
    }
}
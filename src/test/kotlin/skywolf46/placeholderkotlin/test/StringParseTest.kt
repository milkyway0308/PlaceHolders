package skywolf46.placeholderkotlin.test

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderFirst
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderSecond
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderThird
import skywolf46.placeholderskotlin.PlaceHoldersKotlin
import skywolf46.placeholderskotlin.analyzer.StringAnalyzer
import skywolf46.placeholderskotlin.storage.PlaceHolderManager

class StringParseTest {

    @Test
    fun placeHolderFindTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        assertEquals(TestPlaceHolderFirst::class.java, storage.findHolderStorage("<", ">")?.get("test"))
    }

    @Test
    fun multiplePlaceHolderFindTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        storage.registerPlaceholder("[", "]", "test2", TestPlaceHolderSecond::class.java)
        storage.registerPlaceholder("{", "}", "test3", TestPlaceHolderThird::class.java)
        assertEquals(TestPlaceHolderFirst::class.java, storage.findHolderStorage("<", ">")?.get("test"))
        assertEquals(TestPlaceHolderSecond::class.java, storage.findHolderStorage("[", "]")?.get("test2"))
        assertEquals(TestPlaceHolderThird::class.java, storage.findHolderStorage("{", "}")?.get("test3"))
    }


    @Test
    fun requireFailedMultiplePlaceHolderFindTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        storage.registerPlaceholder("[", "]", "test2", TestPlaceHolderSecond::class.java)
        storage.registerPlaceholder("{", "}", "test3", TestPlaceHolderThird::class.java)
        assertEquals(null, storage.findHolderStorage("<", ">")?.get("test2"))
        assertEquals(null, storage.findHolderStorage("<", ">")?.get("test3"))
        assertEquals(null, storage.findHolderStorage("[", "]")?.get("test1"))
        assertEquals(null, storage.findHolderStorage("[", "]")?.get("test3"))
        assertEquals(null, storage.findHolderStorage("{", "}")?.get("test1"))
        assertEquals(null, storage.findHolderStorage("{", "}")?.get("test2"))
    }

    @Test
    fun singlePlaceHolderParseTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        val targetString = "test, <test>,asdf,<test><test><Test>"
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        assertEquals(
            targetString
                .replace("<test>", "Test"),
            parser.analyze(arrayOf(),
                ArgumentStorage(),
                targetString)
                .parse(ArgumentStorage())
        )
    }

    @Test
    fun multiplePlaceHolderParseTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        val targetString = "test, <test2>,asdf,<test><test><Test> <Test2> <test3>.<test> <Test3><test2>!"
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        storage.registerPlaceholder("<", ">", "test2", TestPlaceHolderSecond::class.java)
        storage.registerPlaceholder("<", ">", "test3", TestPlaceHolderThird::class.java)
        assertEquals(
            targetString
                .replace("<test>", "Test")
                .replace("<test2>", "Test2")
                .replace("<test3>", "Test3"),
            parser.analyze(arrayOf(),
                ArgumentStorage(),
                targetString)
                .parse(ArgumentStorage())
        )
    }

    @Test
    fun multiplePrefixedPlaceHolderParseTest() {
        val storage = PlaceHolderManager()
        val parser = StringAnalyzer(storage)
        val targetString =
            "test, <test2>[test2]<test2>[test2],asdf,<test><test><Test> <Test2> {test3}<test3}[test3]<test3>.<test> <Test3><test2>!"
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        storage.registerPlaceholder("[", "]", "test2", TestPlaceHolderSecond::class.java)
        storage.registerPlaceholder("{", "}", "test3", TestPlaceHolderThird::class.java)
        assertEquals(
            targetString
                .replace("<test>", "Test")
                .replace("[test2]", "Test2")
                .replace("{test3}", "Test3"),
            parser.analyze(arrayOf(),
                ArgumentStorage(),
                targetString)
                .parse(ArgumentStorage()).apply {
                    println("Final: $this")
                }
        )
    }
}
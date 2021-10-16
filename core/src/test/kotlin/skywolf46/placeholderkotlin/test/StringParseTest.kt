package skywolf46.placeholderkotlin.test

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderkotlin.test.data.TestTarget
import skywolf46.placeholderkotlin.test.impl.TestParameterHolder
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderFirst
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderSecond
import skywolf46.placeholderkotlin.test.impl.TestPlaceHolderThird
import skywolf46.placeholderskotlin.PlaceHolders.DEFAULT_STORAGE
import skywolf46.placeholderskotlin.PlaceHolders.compile
import skywolf46.placeholderskotlin.PlaceHolders.compileWith

class StringParseTest {

    @Test
    fun placeHolderFindTest() {
        val (targetString, storage, parser) = TestTarget("")
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        assertEquals(TestPlaceHolderFirst::class.java, storage.findHolderStorage("<", ">")?.get("test"))
    }

    @Test
    fun multiplePlaceHolderFindTest() {
        val (targetString, storage, parser) = TestTarget("")
        storage.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        storage.registerPlaceholder("[", "]", "test2", TestPlaceHolderSecond::class.java)
        storage.registerPlaceholder("{", "}", "test3", TestPlaceHolderThird::class.java)
        assertEquals(TestPlaceHolderFirst::class.java, storage.findHolderStorage("<", ">")?.get("test"))
        assertEquals(TestPlaceHolderSecond::class.java, storage.findHolderStorage("[", "]")?.get("test2"))
        assertEquals(TestPlaceHolderThird::class.java, storage.findHolderStorage("{", "}")?.get("test3"))
    }


    @Test
    fun requireFailedMultiplePlaceHolderFindTest() {
        val (targetString, storage, parser) = TestTarget("")
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
        val (targetString, storage, parser) = TestTarget("test, <test>,asdf,<test><test><Test>")
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
        val (targetString, storage, parser) = TestTarget("test, <test2>,asdf,<test><test><Test> <Test2> <test3>.<test> <Test3><test2>!")
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
        val (targetString, storage, parser) = TestTarget("test, <test2>[test2]<test2>[test2],asdf,<test><test><Test> <Test2> {test3}<test3}[test3]<test3>.<test> <Test3><test2>!")
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
                .parse(ArgumentStorage())
        )
    }

    @Test
    fun parameterizedPlaceHolderTest() {
        val next = 50
        val (targetString, storage, parser) = TestTarget("$next is <oct:$next>.")
        parser.manager.registerPlaceholder("<", ">", "oct", TestParameterHolder::class.java)
        println(parser.analyze(arrayOf(), storage, targetString).parse(storage))
    }

    @Test
    fun parameterizedPlaceHolderTest2() {
        val next = 50
        val (targetString, storage, parser) = TestTarget("$next is <oct:$next> <test> <test4>.")
        parser.manager.registerPlaceholder("<", ">", "oct", TestParameterHolder::class.java)
        DEFAULT_STORAGE.registerPlaceholder("<", ">", "oct", TestParameterHolder::class.java)
        DEFAULT_STORAGE.registerPlaceholder("<", ">", "test", TestPlaceHolderFirst::class.java)
        println(parser.analyze(arrayOf(), storage, targetString).parse(storage))
        println(targetString.compile(ArgumentStorage()).parse(ArgumentStorage().apply {
            setArgument("test4", "Test!")
        }))
        println(targetString.compileWith(parser, ArgumentStorage()).parse(ArgumentStorage()))
    }
}
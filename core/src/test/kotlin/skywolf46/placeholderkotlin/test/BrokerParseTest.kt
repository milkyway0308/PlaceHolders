package skywolf46.placeholderkotlin.test

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import skywolf46.placeholderkotlin.test.data.TestTarget
import skywolf46.placeholderskotlin.impl.broker.SelfReplacingHolderBroker
import skywolf46.placeholderkotlin.test.impl.interceptor.TempPlaceholderReplacer

class BrokerParseTest {
    @Test
    fun checkIntercept() {
        val (targetString, storage, parser) = TestTarget("Hello, <world>. <Coded> to work and not to feel>")
        parser.addBroker(TempPlaceholderReplacer())
        parser.manager.registerPrefixSuffix("<", ">")
        assertEquals("Hello, <Intercepted>. <Intercepted> to work and not to feel>",
            parser.analyze(arrayOf(), storage, targetString).parse(storage))
    }

    @Test
    fun replaceTemporaryHolderWithInterceptor() {
        val (targetString, storage, parser) = TestTarget("Hello, <world>. <Coded> to work and not to feel>")
        parser.addBroker(SelfReplacingHolderBroker)
        storage["Coded"] = "Programmed"
        parser.manager.registerPrefixSuffix("<", ">")
        println(parser.analyze(arrayOf(), storage, targetString).parse(storage))
        assertEquals(
            "Hello, <world>. Programmed to work and not to feel>",
            parser.analyze(arrayOf(), storage, targetString).parse(storage))
    }
}
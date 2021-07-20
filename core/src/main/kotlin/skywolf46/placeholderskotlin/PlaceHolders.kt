package skywolf46.placeholderskotlin

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.analyzer.StringAnalyzer
import skywolf46.placeholderskotlin.annotations.PlaceHolder
import skywolf46.placeholderskotlin.data.WrappedString
import skywolf46.placeholderskotlin.impl.broker.SelfReplacingHolderBroker
import skywolf46.placeholderskotlin.storage.PlaceHolderManager

object PlaceHolders {
    val DEFAULT_STORAGE = PlaceHolderManager()
    val DEFAULT_ANALYZER = StringAnalyzer(DEFAULT_STORAGE)
        .apply {
            addBroker(SelfReplacingHolderBroker)
        }
    val DEFAULT_PURE_ANALYZER = StringAnalyzer(DEFAULT_STORAGE)

    fun String.compile(storage: ArgumentStorage, pure: Boolean = false): WrappedString {
        return compileWith(if (pure) DEFAULT_PURE_ANALYZER else DEFAULT_ANALYZER, storage)
    }

    fun String.compileWith(analyzer: StringAnalyzer, storage: ArgumentStorage): WrappedString {
        return analyzer.analyze(arrayOf(), storage, this)
    }

    fun init(cls: List<Class<*>>) {
        for (x in cls) {
            x.getAnnotation(PlaceHolder::class.java)?.apply {
                if (!AbstractPlaceHolder::class.java.isAssignableFrom(x))
                    return@apply
                DEFAULT_STORAGE.registerPlaceholder(this.prefix, this.suffix, this.content,
                    x as Class<out AbstractPlaceHolder>)
            }
        }
    }
}
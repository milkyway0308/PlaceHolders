package skywolf46.placeholderskotlin.storage

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.PlaceHolders.DEFAULT_STORAGE
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder

class PlaceHolderManager : ArgumentStorage() {
    companion object {
        fun createProxiedStorage(): PlaceHolderManager {
            return PlaceHolderManager().apply {
                addProxy(DEFAULT_STORAGE)
            }
        }
    }

    init {
        addArgument(PlaceHolderPrefixInspector())
    }

    fun findHolderStorage(prefix: String, suffix: String): PlaceHolderStorage? {
        return getPrefixInspector()
            .getValue(prefix)
            ?.getValue(suffix)
    }


    fun isAcceptableState(prefix: String): Pair<Boolean, PlaceHolderSuffixInspector?> {
        return getPrefixInspector().getAcceptableValue(prefix)
    }


    fun isAcceptableState(prefix: String, suffix: String): Pair<Boolean, PlaceHolderStorage?> {
        val next = getPrefixInspector().getValue(prefix) ?: return false to null
        return next.getAcceptableValue(suffix)
    }

    fun getPrefixInspector(): PlaceHolderPrefixInspector {
        return get(PlaceHolderPrefixInspector::class)[0]
    }

    fun registerPrefixSuffix(prefix: String, suffix: String): PlaceHolderManager {
        getPrefixInspector().registerStorage(prefix, suffix)
        return this
    }

    fun registerPlaceholder(
        prefix: String,
        suffix: String,
        name: String,
        holder: Class<out AbstractPlaceHolder>,
    ): PlaceHolderManager {
        println("Registered holder $prefix$name$suffix")
        getPrefixInspector().getOrRegisterStorage(prefix, suffix)[name] = holder
        return this
    }

}
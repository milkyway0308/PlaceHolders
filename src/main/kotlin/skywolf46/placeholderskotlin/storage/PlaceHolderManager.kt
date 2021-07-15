package skywolf46.placeholderskotlin.storage

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.PlaceHoldersKotlin

class PlaceHolderManager : ArgumentStorage() {
    companion object {
        fun createProxiedStorage(): PlaceHolderManager {
            return PlaceHolderManager().apply {
                addProxy(PlaceHoldersKotlin.DEFAULT_STORAGE)
            }
        }
    }

    init {
        addArgument(CharacterInspector<PlaceHolderSuffixInspector>())
    }

    fun findHolderStorage(starter: String, ender: String): PlaceHolderSuffixInspector {
        TODO("Not yet implemented")

    }


}
package skywolf46.placeholderskotlin.storage

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.PlaceHoldersKotlin

class PlaceHolderStorage : ArgumentStorage() {
    companion object {
        fun createProxiedStorage(): PlaceHolderStorage {
            return PlaceHolderStorage().apply {
                addProxy(PlaceHoldersKotlin.DEFAULT_STORAGE)
            }
        }
    }

    override fun set(key: String, any: Any) {
        super.set(key, any)
    }


}
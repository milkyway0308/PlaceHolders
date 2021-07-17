package skywolf46.placeholderskotlin.data

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.abstraction.AbstractPlaceHolder
import skywolf46.placeholderskotlin.storage.PlaceHolderManager

data class ArgumentData(
    val original: String,
    val prefix: String,
    val suffix: String,
    val content: String,
    val parameters: ArgumentStorage,
) {
    fun toPlaceHolder(manager: PlaceHolderManager): AbstractPlaceHolder? {
        return manager.findHolderStorage(prefix, suffix)?.get(content)?.newInstance()?.apply {
            onRegister(manager)
        }
    }
}
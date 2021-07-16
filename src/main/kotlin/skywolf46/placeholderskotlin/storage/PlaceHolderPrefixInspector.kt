package skywolf46.placeholderskotlin.storage

class PlaceHolderPrefixInspector : CharacterInspector<PlaceHolderSuffixInspector>() {
    fun registerStorage(prefix: String, suffix: String) {
        registerValue(prefix, PlaceHolderSuffixInspector().apply {
            registerValue(suffix, PlaceHolderStorage())
        })
    }

    fun getOrRegisterStorage(prefix: String, suffix: String): PlaceHolderStorage {
        return (getValue(prefix)?.getValue(suffix) ?: PlaceHolderStorage().also {
            registerValue(prefix, PlaceHolderSuffixInspector().apply {
                registerValue(suffix, it)
            })
        })
    }
}
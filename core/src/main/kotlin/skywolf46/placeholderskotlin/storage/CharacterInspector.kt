package skywolf46.placeholderskotlin.storage

open class CharacterInspector<X : Any> {

    internal val deepInspected = mutableMapOf<Char, CharacterInspector<X>>()
    var value: X? = null

    fun isAcceptable(key: String): Boolean {
        return checkAcceptableRecursive(0, key)
    }

    private fun checkAcceptableRecursive(pointer: Int, key: String): Boolean {
        if (pointer == key.length)
            return true
        return deepInspected[key[pointer]]?.checkAcceptableRecursive(pointer + 1, key) ?: false
    }

    fun getValue(key: String): X? {
        return getValueRecursively(0, key)
    }

    private fun getValueRecursively(pointer: Int, key: String): X? {
        if (pointer == key.length)
            return value
        return deepInspected[key[pointer]]?.getValueRecursively(pointer + 1, key)
    }


    fun getAcceptableValue(key: String): Pair<Boolean, X?> {
        return getAcceptableValueRecursively(0, key)
    }

    private fun getAcceptableValueRecursively(pointer: Int, key: String): Pair<Boolean, X?> {
        if (pointer == key.length)
            return true to value
        return deepInspected[key[pointer]]?.getAcceptableValueRecursively(pointer + 1, key) ?: (false to null)
    }

    fun registerIfNotExists(key: String) {
        registerIfNotExistsRecursively(0, key)
    }


    private fun registerIfNotExistsRecursively(pointer: Int, key: String) {
        if (pointer >= key.length) {
            return
        }
        deepInspected.computeIfAbsent(key[pointer]) {
            CharacterInspector()
        }.registerIfNotExistsRecursively(pointer + 1, key)
    }

    fun registerValue(key: String, data: X) {
        registerRecursive(0, key, data)
    }

    private fun registerRecursive(pointer: Int, key: String, data: X) {
        if (pointer >= key.length) {
            value = data
            return
        }
        deepInspected.computeIfAbsent(key[pointer]) {
            CharacterInspector()
        }.registerRecursive(pointer + 1, key, data)
    }

}
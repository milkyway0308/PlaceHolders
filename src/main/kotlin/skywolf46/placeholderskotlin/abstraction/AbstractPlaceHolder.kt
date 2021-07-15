package skywolf46.placeholderskotlin.abstraction


import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.placeholderskotlin.data.ArgumentData
import skywolf46.placeholderskotlin.storage.PlaceHolderStorage

/**
 * Base class of placeholders.
 * If @PlaceHolder annotation is attached to [AbstractPlaceHolder] class, PlaceHolders library will register placeholder automatically.
 *
 *  Warning : If SkywolfExtraUtility run with restricted mode in your server,
 *  You have to add [skywolf46.extrautility.annotations.AllowScanning] annotation to your plugin core (JavaPlugin implementation).
 */
abstract class AbstractPlaceHolder(val data: ArgumentData) {
    val parameter = data.parameters

    /**
     * Called when current holder registered to storage.
     * Registration will fail when exception throw from this method.
     */
    open fun onRegister(storage: PlaceHolderStorage) {
        // Empty method for implementation
    }

    /**
     * Called when instance created.
     * Will not called when instance created for registration.
     */
    open fun onCreate() {
        // Empty method for implementation
    }

    /**
     * Called when argument have to process.
     * This method must have to return placeholder processing result.
     * If return value is null, placeholder will be replaced with pure string.
     */
    abstract fun process(inputParam: ArgumentStorage): String?
}
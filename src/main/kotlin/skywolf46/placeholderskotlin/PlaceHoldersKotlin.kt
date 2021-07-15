package skywolf46.placeholderskotlin

import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.placeholderskotlin.storage.PlaceHolderManager

@AllowScanning
class PlaceHoldersKotlin : JavaPlugin() {
    companion object {
        val DEFAULT_STORAGE = PlaceHolderManager()
    }

    override fun onEnable() {

    }
}
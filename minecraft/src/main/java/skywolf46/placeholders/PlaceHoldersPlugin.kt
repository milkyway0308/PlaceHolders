package skywolf46.placeholders

import org.bukkit.plugin.java.JavaPlugin
import skywolf46.extrautility.annotations.AllowScanning
import skywolf46.extrautility.util.MinecraftLoader
import skywolf46.messagereplacer.MessageReplacer
import skywolf46.messagereplacer.util.MessageReplacerUtility.i18n

import skywolf46.placeholderskotlin.PlaceHolders

@AllowScanning
class PlaceHoldersPlugin : JavaPlugin() {
    override fun onEnable() {
        PlaceHolders.init(MinecraftLoader.loadAllClass())
        MessageReplacer.init(MinecraftLoader.loadAllClass())
    }
}
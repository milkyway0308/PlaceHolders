package skywolf46.messagereplacer.provider

import skywolf46.messagereplacer.abstraction.AbstractFileProvider
import skywolf46.messagereplacer.annotations.FileProvider
import java.io.File
import java.lang.Exception
import kotlin.reflect.KClass

class AutoDetectProvider : AbstractFileProvider() {
    init {
        try {
            Class.forName("org.bukkit.Bukkit")
            addChild(JavaPluginProvider())
        } catch (e: Exception) {
            // Ignore
            e.printStackTrace()
        }
    }

    @FileProvider
    override fun provide(target: KClass<*>): File? {
        return super.provide(target)
    }

    override fun provideFile(target: KClass<*>): File {
        return File("messages/${target.java.`package`.name}/${target.simpleName}.yml")
    }

}
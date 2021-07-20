package skywolf46.messagereplacer.provider

import org.bukkit.Bukkit
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.extrautility.util.MethodInvoker
import skywolf46.messagereplacer.abstraction.AbstractFileProvider
import skywolf46.messagereplacer.annotations.FileProvider
import skywolf46.messagereplacer.util.ClassNameSplitter.toProject
import java.io.File
import java.lang.StringBuilder
import kotlin.reflect.KClass

class JavaPluginProvider : AbstractFileProvider() {
    private val cache = mutableMapOf<String, MethodInvoker>()

    init {
        for (x in Bukkit.getPluginManager().plugins) {
            cache[x.javaClass.toProject()] = MethodInvoker(
                x.javaClass.getMethod("getDataFolder"),
                x
            )
        }
    }

    @FileProvider
    override fun provideFile(target: KClass<*>): File {
        val packageDeclaration = target.java.`package`.name.split(".")
        val builder = StringBuilder()
        for (element in packageDeclaration) {
            builder.append(element).append(".")
            cache[builder.substring(0, builder.length - 1)]?.apply {
                return File(invoke(ArgumentStorage()) as File, "messages/")
            }
        }
        return File("/messages/${target.java.`package`.name}/${target.simpleName}/")
    }

}
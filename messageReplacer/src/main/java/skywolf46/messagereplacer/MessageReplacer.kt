package skywolf46.messagereplacer

import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.extrautility.util.MethodInvoker
import skywolf46.extrautility.util.MethodUtil
import skywolf46.extrautility.util.MethodWrapper
import skywolf46.messagereplacer.annotations.FileProvider
import skywolf46.messagereplacer.annotations.MessageConfiguration
import skywolf46.messagereplacer.data.MessageStorage
import skywolf46.messagereplacer.util.ClassNameSplitter.toProject
import java.io.File
import kotlin.reflect.KClass

object MessageReplacer {
    private val messages = mutableMapOf<String, MessageStorage>()
    private val providers = mutableMapOf<KClass<*>, MethodWrapper>()

    var DEFAULT_LANGUAGE = "${System.getProperty("user.language")}-${System.getProperty("user.country")}"

    fun Class<Any>.storage(): MessageStorage? {
        return messages[name]
    }

    fun init(cls: List<Class<*>>) {
        println("MessageReplacer | Initializing MessageReplacer core")
        initializeClasses(cls)
        saveAll()
    }

    private fun initializeClasses(cls: List<Class<*>>) {
        println("MessageReplacer | Scanning for message / file provider")
        for (x in cls) {
            processClass(x.kotlin)
        }
    }

    private fun processClass(x: KClass<*>) {
        x.java.getDeclaredAnnotation(MessageConfiguration::class.java)?.apply {
            if (fileProvider !in providers) {
                extractForProvideFile(fileProvider)?.apply {
                    providers[fileProvider] = this
                } ?: run {
                    System.err.println("MessageReplacer | Failed to init file provider ${fileProvider.qualifiedName} : No @FileProvider method defined")
                    return
                }
            }
            MessageStorage(x.objectInstance ?: x.java.newInstance(),
                providers[fileProvider]!!.invoke(x) as File)
                .apply {
                    collapseWithOriginal(if (boundForProject) x.java.toProject() else x.java.name, this)
                }
            println("MessageReplacer | Registered message configuration ${x.qualifiedName}($language) with file loader ${fileProvider.qualifiedName}")
        }
    }


    private fun collapseWithOriginal(targetString: String, storage: MessageStorage) {
        if (!messages.containsKey(targetString)) {
            messages[targetString] = storage
            return
        }
        messages[targetString]!!.collapse(storage)
    }

    private fun extractForProvideFile(target: KClass<*>): MethodWrapper? {
        val found = MethodUtil.filter(target.java).filter(FileProvider::class.java).methods
        if (found.isEmpty())
            return null
        found[0].method.isAccessible = true
        return MethodWrapper(found[0].method, target.java.newInstance())
    }

    private fun saveAll() {
        println("MessageReplacer | Saving message storages..")
        messages.values.forEach {
            it.save()
        }
    }

}
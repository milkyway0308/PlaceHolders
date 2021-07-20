package skywolf46.messagereplacer.data

import org.bukkit.configuration.file.YamlConfiguration
import skywolf46.extrautility.data.ArgumentStorage
import skywolf46.messagereplacer.annotations.MessageConfiguration
import skywolf46.messagereplacer.util.MassiveYaml
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class MessageStorage(private val instance: Any, private val file: File) {
    companion object {
        private val SHARED_EMPTY_STORAGE = ArgumentStorage()
    }

    private val messages = mutableMapOf<String, MessageData>()

    init {
        val config = instance.javaClass.getDeclaredAnnotation(MessageConfiguration::class.java)
        for (x in instance.javaClass.declaredFields) {
            if (Modifier.isFinal(x.modifiers))
                continue
            registerType(config, x)
        }
        load()
    }

    internal fun save() {
        val yaml = MassiveYaml()
        for ((x, y) in messages)
            y.save(x, yaml)
        yaml.saveAll(file)
    }

    private fun load() {
        println("Try to loading from ${file.name}")
        if (file.exists()) {
            for (x in file.listFiles()) {
                load(x.nameWithoutExtension, YamlConfiguration.loadConfiguration(x))
            }
        }
    }

    private fun load(lang: String, yaml: YamlConfiguration) {
        for (x in yaml.getKeys(false)) {
            if (yaml.isList(x))
                getMessageData(x).update(lang, yaml.getStringList(x))
            else
                getMessageData(x).update(lang, listOf(yaml.getString(x)))
        }
    }

    private fun registerType(config: MessageConfiguration, field: Field) {
        field.isAccessible = true
        if (field.type == String::class.java) {
            registerFromString(field, config.language, field.get(instance) as String)
        } else {
//            println((field.type as ParameterizedType).actualTypeArguments[0])
            if (isSubTypeString(field.genericType)) {
                if (field.type == Array::class.java) {
                    registerFromArray(field, config.language, field.get(instance) as Array<String>)
                } else if (field.type == List::class.java) {
                    registerFromList(field, config.language, field.get(instance) as List<String>)
                }
            }
        }
    }

    private fun registerFromString(field: Field, lang: String, str: String) {
        getMessageData(field.name).apply {
            update(lang, listOf(str))
            this[lang] = getID(lang)
            field.set(instance, getRawID())
        }
    }

    private fun registerFromArray(field: Field, lang: String, array: Array<String>) {
        getMessageData(field.name).apply {
            update(lang, array.toList())
            this[lang] = getID(lang)
            field.set(instance, arrayOf(getRawID()))
        }
    }


    private fun registerFromList(field: Field, lang: String, list: List<String>) {
        getMessageData(field.name).apply {
            update(lang, list)
            this[lang] = getID(lang)
            field.set(instance, listOf(getRawID()))
        }
    }

    private fun getMessageData(name: String): MessageData {
        return messages.computeIfAbsent(name) {
            MessageData(instance.javaClass, it)
        }
    }

    private fun isSubTypeString(type: Type): Boolean {
        return type is ParameterizedType && type.actualTypeArguments[0] == String::class.java
    }

    fun collapse(messageStorage: MessageStorage) {
        for ((x, y) in messageStorage.messages) {
            messages[x]?.apply {
                collapse(y)
            } ?: kotlin.run {
                messages[x] = y
            }
        }
    }

}

package skywolf46.messagereplacer.util

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class MassiveYaml : HashMap<String, YamlConfiguration>() {
    override fun get(key: String): YamlConfiguration {
        return computeIfAbsent(key) { YamlConfiguration() }
    }

    fun saveAll(baseFile: File) {
        println("MassiveYaml: Saving to ${baseFile.absolutePath}")
        for ((x, y) in this) {
            y.save(File(baseFile, "$x.yml"))
        }
    }
}
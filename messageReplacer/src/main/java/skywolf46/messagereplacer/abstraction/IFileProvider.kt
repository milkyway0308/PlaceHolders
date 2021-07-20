package skywolf46.messagereplacer.abstraction

import java.io.File
import kotlin.reflect.KClass

interface IFileProvider {
    fun provide(target: KClass<*>): File?
    fun addChild(provider: IFileProvider)
}
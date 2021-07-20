package skywolf46.messagereplacer.abstraction

import java.io.File
import kotlin.reflect.KClass

abstract class AbstractFileProvider : IFileProvider {
    private val childs = mutableListOf<IFileProvider>()
    override fun addChild(provider: IFileProvider) {
        childs += provider
    }

    override fun provide(target: KClass<*>): File? {
        for (x in childs.size downTo 1) {
            val file = childs[x - 1].provide(target)
            if (file != null)
                return file
        }
        return provideFile(target)
    }

    protected abstract fun provideFile(target: KClass<*>): File?
}
package skywolf46.messagereplacer.annotations

import skywolf46.messagereplacer.provider.AutoDetectProvider
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class MessageConfiguration(
    val language: String = "en_US",
    val boundForProject: Boolean = true,
    val isDefault: Boolean = false,
    val fileProvider: KClass<out Any> = AutoDetectProvider::class
)
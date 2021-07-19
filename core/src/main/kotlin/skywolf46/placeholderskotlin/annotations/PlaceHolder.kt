package skywolf46.placeholderskotlin.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PlaceHolder(val content: String, val prefix: String = "<", val suffix: String = ">")
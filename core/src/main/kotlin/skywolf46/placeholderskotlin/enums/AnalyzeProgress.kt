package skywolf46.placeholderskotlin.enums

enum class AnalyzeProgress {
    /**
     * Called before the start of analysis.
     */
    READY,

    /**
     * Called before every step of analysis.
     * For example, his step is called before one placeholder has been resolved.
     * Developer can intercept arguments to change the result of placeholder.
     */
    ANALYZING,

    /**
     * Called when analysis result is derived.
     * For example, during placeholder analysis, this step is called after one placeholder has been resolved.
     */
    ANALYZED,

    /**
     * Called before the end of analysis.
     */
    COMPLETE
}
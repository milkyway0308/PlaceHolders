package skywolf46.placeholders.test

import skywolf46.messagereplacer.annotations.MessageConfiguration

@MessageConfiguration(language = "en_US")
object TestMessageConfig2 {
    var WTF_TEST: List<String> = listOf(
        "test!"
    )
    var WARNING_TEST: List<String> = listOf(
        "Test.."
    )


    var PLAYER_MSG_TEST: List<String> = listOf(
        "Hello, <player>! Your location is <locs>."
    )


    var PLAYER_LOC_TEST: List<String> = listOf(
        "Current Location: <locX>, <locY>, <locZ> (<locs:4>)"
    )

}
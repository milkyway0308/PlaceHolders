package skywolf46.placeholders.impl.players

import org.bukkit.entity.Player
import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.placeholderskotlin.abstraction.AbstractCompactPlaceHolder
import skywolf46.placeholderskotlin.annotations.PlaceHolder

@PlaceHolder("player")
class PlayerNameHolder : AbstractCompactPlaceHolder() {
    override fun process(data: Arguments): String? {
        data.params<Player> {
            return it.name
        }
        return null
    }
}
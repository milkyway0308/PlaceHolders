package skywolf46.placeholders.impl.players.locations

import org.bukkit.entity.Player
import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.placeholderskotlin.abstraction.AbstractCompactPlaceHolder
import skywolf46.placeholderskotlin.annotations.PlaceHolder

@PlaceHolder("locs")
class PlayerLocationPlaceHolder : AbstractCompactPlaceHolder() {
    override fun process(data: Arguments): String? {
        data.params<Player> { player ->
            val loc = player.location
            data.args<Int> { len ->
                return "%.${len}f, %.${len}f, %.${len}f ".format(loc.x, loc.y, loc.z).trim('0')
            }
            return "${loc.blockX}, ${loc.blockY}, ${loc.blockZ}"
        }
        return null
    }
}
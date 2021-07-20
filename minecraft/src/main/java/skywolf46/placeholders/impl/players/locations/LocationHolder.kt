package skywolf46.placeholders.impl.players.locations

import org.bukkit.Location
import org.bukkit.entity.Player
import skywolf46.commandannotation.kotlin.data.Arguments
import skywolf46.placeholderskotlin.abstraction.AbstractCompactPlaceHolder

abstract class LocationHolder : AbstractCompactPlaceHolder(){
    override fun process(data: Arguments): String? {
        data.params<Player> { player ->
            val loc = player.location
            data.args<Int> { len ->
                return "%.${len}f".format(getLocationTarget(loc)).trim('0')
            }
            return getLocationTarget(loc).toString()
        }
        return null
    }

    protected abstract fun getLocationTarget(location: Location) : Double
}
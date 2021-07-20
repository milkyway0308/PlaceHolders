package skywolf46.placeholders.impl.players.locations

import org.bukkit.Location
import skywolf46.placeholderskotlin.annotations.PlaceHolder

@PlaceHolder("locZ")
class PlayerLocationZHolder : LocationHolder() {
    override fun getLocationTarget(location: Location): Double {
        return location.z
    }
}
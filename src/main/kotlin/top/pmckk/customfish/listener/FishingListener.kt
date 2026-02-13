package top.pmckk.customfish.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent

class FishingListener : Listener{
    @EventHandler
    fun onPlayerFishing(e: PlayerFishEvent){
        val player = e.player
        player.sendMessage("你正在钓鱼.")
    }
}
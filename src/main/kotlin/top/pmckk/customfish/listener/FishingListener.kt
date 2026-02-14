package top.pmckk.customfish.listener

import org.bukkit.Bukkit
import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.inventory.ItemStack
import top.pmckk.customfish.config.RewardPoolConfig

class FishingListener : Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onPlayerFishing(event: PlayerFishEvent) {
        val player = event.player

        //仅处理钓到物品战利品的状态
        if (event.state != PlayerFishEvent.State.CAUGHT_FISH) return

        //安全获取原版物品实体
        val originalItem = event.caught ?: run {
            return
        }
        //仅处理物品实体
        if (originalItem !is Item) return

        //从奖池获取自定义奖励
        val randomReward = RewardPoolConfig.getRandomReward() ?: run {
            player.sendMessage("§c当前无可用的钓鱼奖池，未钓到任何物品！")
            return
        }

        //构建自定义物品
        val customItem = ItemStack(randomReward.material)
        customItem.itemMeta = customItem.itemMeta?.apply {
            setDisplayName(randomReward.name)
            lore = randomReward.lore
        }
        //替换原版物品的内容
        originalItem.itemStack = customItem
        originalItem.pickupDelay = 0 // 玩家可立即拾取

        //奖励播报+执行命令
        if (randomReward.broadcast) {
            Bukkit.broadcastMessage("§6[钓鱼大奖] §a${player.name} 钓到了稀有战利品：${randomReward.name}！")
        } else {
            player.sendMessage("§a你钓到了：${randomReward.name}！")
        }

        //执行额外命令（替换玩家名占位符）
        randomReward.commands.forEach { command ->
            if (command.isNotBlank()) {
                val finalCommand = command.replace("%player_name%", player.name)
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCommand)
            }
        }
    }
}
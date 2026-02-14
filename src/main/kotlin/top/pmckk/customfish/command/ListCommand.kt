package top.pmckk.customfish.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.pmckk.customfish.config.ConfigManager

object ListCommand {

    fun execute(sender: CommandSender) {
        //权限校验
        if (!sender.isOp && !sender.hasPermission("customfishing.list") && sender is Player) {
            sender.sendMessage("§c你没有权限查看奖励池列表！需要管理员权限。")
            return
        }

        //读取奖池配置
        val poolIds = ConfigManager.rewardpoolConfig.getKeys(false)
        if (poolIds.isEmpty()) {
            sender.sendMessage("§c奖励池配置为空！请检查 rewardpool.yml 配置。")
            return
        }

        //拼接并发送奖池列表
        sender.sendMessage("§6钓鱼奖池列表共: ${poolIds.size} 个")
        poolIds.forEachIndexed { index, poolId ->
            val poolPath = "$poolId."
            val poolName = ConfigManager.rewardpoolConfig.getString("${poolPath}name") ?: "未命名奖池"
            val isEnabled = ConfigManager.rewardpoolConfig.getBoolean("${poolPath}enable", false)
            val rewardListPath = "${poolPath}reward_list."
            val rewardCount = ConfigManager.rewardpoolConfig.getConfigurationSection(rewardListPath)?.getKeys(false)?.size ?: 0
            val enableStatus = if (isEnabled) "§a已启用" else "§c未启用"
            sender.sendMessage("§6${index + 1}. §fID：$poolId | 名称：$poolName | 状态：$enableStatus | 奖励数量：$rewardCount")
        }
    }
}
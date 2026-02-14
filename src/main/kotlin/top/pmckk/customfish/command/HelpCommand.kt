package top.pmckk.customfish.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object HelpCommand {

    fun sendHelp(sender: CommandSender) {
        // 权限判断
        val isAdmin = sender.isOp || sender.hasPermission("customfishing.admin")
        val isConsole = sender !is Player

        sender.sendMessage("§eCustomFishing 帮助菜单:")

        sender.sendMessage("§6/customfishing help §f- 查看此帮助信息")
        sender.sendMessage("§6/customfishing gui shop §f- 打开钓鱼商店")
        sender.sendMessage("§6/customfishing gui game §f- 打开钓鱼小游戏菜单")

        if (isAdmin || isConsole) {
            sender.sendMessage("§6/customfishing list §f- 查看奖励池列表")
            sender.sendMessage("§6/customfishing reload [config/rewardpool/all] §f- 重载配置")
            sender.sendMessage("§6/customfishing gui edit §f- 打开编辑菜单")
            sender.sendMessage("§6/customfishing game §f- 钓鱼小游戏管理")
        }
    }
}
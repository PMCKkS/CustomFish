package top.pmckk.customfish.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object GuiCommand {
    val subGuiCommands = listOf("shop", "edit", "game","reward")

    fun execute(sender: CommandSender, args: Array<out String?>) {
        if (sender !is Player) {
            sender.sendMessage("§c只有玩家才可以打开GUI界面.")
            return
        }

        if (args.size < 2 || args[1] == null) {
            sender.sendMessage("§c请指定GUI类型！如：/customfishing gui shop.")
            sender.sendMessage("§7可选类型：shop,game,reward.")
            return
        }
        when (args[1]!!.lowercase()) {
            "shop" -> openShopGui(sender)
            "game" -> openGameGui(sender)
            "edit" -> openEditGui(sender)
            "reward" -> openRewardGui(sender)
            else -> sender.sendMessage("§c无效的GUI类型,请选择:shop,game,reward.")
        }
    }

    /**
     * 当前只完成的打开菜单发送消息
     * 等完善
     */
    private fun openShopGui(player: Player) {
        player.sendMessage("§e你打开了钓鱼商店")
    }

    private fun openGameGui(player: Player) {
        player.sendMessage("§e你打开了钓鱼小游戏界面")
    }
    private fun openRewardGui(player: Player) {
        player.sendMessage("§e你打开了奖励界面界面")
    }

    private fun openEditGui(player: Player) {
        if (!player.isOp && !player.hasPermission("customfishing.gui.edit")) {
            player.sendMessage("§c你没有权限使用编辑菜单.")
            return
        }
        player.sendMessage("§e你打开了编辑菜单.")
    }

    fun getTabCompletions(sender: CommandSender, arg: String?): MutableList<String> {
        val completions = mutableListOf<String>()
        val availableCommands = if (sender.isOp || sender.hasPermission("customfishing.gui.edit")) {
            subGuiCommands
        } else {
            subGuiCommands.filter { it != "edit" }
        }
        availableCommands.forEach {
            if (it.startsWith(arg ?: "", ignoreCase = true)) {
                completions.add(it)
            }
        }
        return completions
    }
}
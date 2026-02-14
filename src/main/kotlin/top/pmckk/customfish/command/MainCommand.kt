package top.pmckk.customfish.command
import top.pmckk.customfish.config.ConfigManager

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import top.pmckk.customfish.config.RewardPoolConfig

class MainCommand : TabExecutor{
    private val subCommands = listOf("help","list","reload","gui","game")

    //处理命令
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String?>
    ): Boolean {
        if (args.isEmpty() || args[0] == null) {
            HelpCommand.sendHelp(sender)
            return true
        }

        //匹配子命令
        when (args[0]!!.lowercase()) {
            "help" -> HelpCommand.sendHelp(sender)
            "list" -> ListCommand.execute(sender)
            "reload" -> reloadConfig(sender, args)
            "gui" -> GuiCommand.execute(sender, args)
            "game" -> GameCommand.execute(sender)
            else -> {
                sender.sendMessage("§c未知命令！输入 /customfishing help 查看帮助。")
                HelpCommand.sendHelp(sender)
            }
        }
        return true
    }
    //命令补全
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String?>
    ): MutableList<String> {
        val completions = mutableListOf<String>()

        //第一层补全
        if (args.size == 1) {
            subCommands.forEach {
                if (it.startsWith(args[0] ?: "", ignoreCase = true)) {
                    // 普通玩家隐藏list/game（仅管理员可见）
                    if ((it == "list" || it == "game") && !sender.isOp && !sender.hasPermission("customfishing.admin")) {
                        return@forEach
                    }
                    completions.add(it)
                }
            }
        }
        else if (args.size == 2 && args[0]!!.lowercase() == "reload") {
            val configFiles = listOf("config", "rewardpool", "all")
            configFiles.forEach {
                if (it.startsWith(args[1] ?: "", ignoreCase = true)) {
                    completions.add(it)
                }
            }
        }
        else if (args.size == 2 && args[0]!!.lowercase() == "gui") {
            completions.addAll(GuiCommand.getTabCompletions(sender, args[1]))
        }

        return completions
    }
    private fun reloadConfig(sender: CommandSender, args: Array<out String?>) {
        if (!sender.isOp && !sender.hasPermission("customfishing.reload") && sender is Player) {
            sender.sendMessage("§c你没有权限重载配置！需要管理员权限。")
            return
        }

        // 处理重载目标
        val target = if (args.size >= 2 && args[1] != null) args[1]!!.lowercase() else "all"
        when (target) {
            "config" -> ConfigManager.reloadConfig("config.yml")
            "rewardpool" -> {
                RewardPoolConfig.reload()
                sender.sendMessage("§a已重新加载奖池配置!")
            }
            "game" -> ConfigManager.reloadConfig("game.yml")
            "shop" -> ConfigManager.reloadConfig("shop.yml")
            else -> {
                sender.sendMessage("§c无效的配置名！可选：config,rewardpool,all")
                return
            }
        }
        sender.sendMessage("§a已成功重载 ${target.uppercase()} 配置文件！")
    }
}
package top.pmckk.customfish.command

import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin

class CommandManager(private val plugin: JavaPlugin) {
    fun registerAllCommands(){
        registerCommand("customfishing", MainCommand())
    }
    private fun registerCommand(commandName: String,executor: CommandExecutor){
        val pluginCommand: PluginCommand?= plugin.getCommand(commandName)

        if (pluginCommand != null){
            pluginCommand.setExecutor(executor)
            if (executor is TabCompleter){
                pluginCommand.setTabCompleter(executor as TabCompleter)
            }
            plugin.logger.info("命令 /$commandName 注册成功!")
        }else{
         plugin.logger.severe("命令 /$commandName 注册失败!")
        }
    }
}
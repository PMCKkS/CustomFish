package top.pmckk.customfish

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.pmckk.customfish.command.CommandManager
import top.pmckk.customfish.config.ConfigManager
import top.pmckk.customfish.config.RewardPoolConfig
import top.pmckk.customfish.listener.FishingListener

class Customfishing : JavaPlugin() {

    companion object{
        lateinit var instance : Customfishing
            private set
    }

    override fun onEnable() {
        //单例实例化
        instance = this;

        //初始化配置管理器
        ConfigManager.init(this)

        //初始化奖池配置
        RewardPoolConfig.init()

        //注册监听
        server.pluginManager.registerEvents(FishingListener(), this)

        //注册命令
        CommandManager(this).registerAllCommands()
        Bukkit.getLogger().info("§a自定义钓鱼插件加载成功")
    }

    override fun onDisable() {
        Bukkit.getLogger().info("§a自定义钓鱼已卸载")
    }
}

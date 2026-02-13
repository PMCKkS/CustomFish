package top.pmckk.customfish

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import top.pmckk.customfish.config.ConfigManager
import top.pmckk.customfish.listener.FishingListener

class Customfish : JavaPlugin() {

    companion object{
        lateinit var instance : Customfish
            private set
    }

    override fun onEnable() {
        //单例实例化
        instance = this;
        //初始化配置管理器
        ConfigManager.init(this)
        //注册监听
        server.pluginManager.registerEvents(FishingListener(), this)
        Bukkit.getLogger().info("自定义钓鱼插件加载成功")
    }

    override fun onDisable() {
        Bukkit.getLogger().info("自定义钓鱼已卸载")
    }
}

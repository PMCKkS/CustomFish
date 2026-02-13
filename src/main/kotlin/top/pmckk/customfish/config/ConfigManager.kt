package top.pmckk.customfish.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

object ConfigManager {
    //插件实例
    private lateinit var plugin: Plugin

    //配置对象
    lateinit var mainConfig : YamlConfiguration
        private set
    lateinit var rewardpoolConfig : YamlConfiguration
        private set

    fun init(pluginInstance: JavaPlugin){
        plugin = pluginInstance
        //检查配置是否存在
        if (!plugin.dataFolder.exists()){
            plugin.dataFolder.mkdirs()
        }
        //加载配置文件
        mainConfig = loadCustomConfig("config.yml")
        rewardpoolConfig = loadCustomConfig("rewardpool.yml")
    }
    //加载配置
    private fun loadCustomConfig(fileName: String): YamlConfiguration{
        val configFile = File(plugin.dataFolder,fileName)
        if (!configFile.exists()){
            plugin.saveResource(fileName,false)
            plugin.logger.info("配置文件加载成功.")
        }
        return YamlConfiguration.loadConfiguration(configFile)
    }
    //保存配置文件
    fun saveCustomConfig(config: YamlConfiguration,fileName: String){
        //捕获异常看的懂吧
        try {
            config.save(File(plugin.dataFolder,fileName))
            plugin.logger.info("配置文件 $fileName 成功保存.")
        }catch (e: Exception){
            plugin.logger.severe("保存配置文件$fileName 失败${e.message}.")
        }
    }
    fun reloadConfig(fileName: String){
        when (fileName.lowercase()){
            "config.yml" -> mainConfig = loadCustomConfig(fileName)
            "rewardpool.yml" -> rewardpoolConfig = loadCustomConfig(fileName)
            else -> plugin.logger.warning("重载 $fileName 失败,请查看格式是否正确.")
        }
        plugin.logger.info("重载配置 $fileName 成功.")
    }
}
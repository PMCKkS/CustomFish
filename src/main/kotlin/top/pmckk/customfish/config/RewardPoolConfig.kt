package top.pmckk.customfish.config

import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import top.pmckk.customfish.Customfishing

//单个奖励
data class FishingReward(
    val chance: Double,
    val name: String,
    val lore: List<String>,
    val material: Material,
    val broadcast: Boolean,
    val commands: List<String>
)
//单个奖池
data class FishingRewardPool(
    val name: String,
    val enable: Boolean,
    val broadcast: Boolean,
    val rewards: List<FishingReward>
)

object RewardPoolConfig {
    private val rewardPools = mutableMapOf<String, FishingRewardPool>()

    //初始化方法以及解析奖池配置
    fun init() {
        parseRewardPools(ConfigManager.rewardpoolConfig)
        Customfishing.instance.logger.info("已加载 ${rewardPools.size} 个钓鱼奖池配置！")
    }

    private fun parseRewardPools(config: YamlConfiguration) {
        rewardPools.clear()
        //获取所有奖池节点
        val poolKeys = config.getKeys(false)

        poolKeys.forEach { poolId ->
            val poolPath = "$poolId."

            //解析奖池基础信息
            val poolName = config.getString("${poolPath}name") ?: "默认奖池"
            val enable = config.getBoolean("${poolPath}enable", false)
            val broadcast = config.getBoolean("${poolPath}broadcast", false)

            //解析奖池内的奖励列表
            val rewardListPath = "${poolPath}reward_list."
            val rewardKeys = config.getConfigurationSection(rewardListPath)?.getKeys(false) ?: emptySet()
            val rewards = mutableListOf<FishingReward>()

            rewardKeys.forEach { rewardKey ->
                val rewardPath = "${rewardListPath}$rewardKey."
                val chance = config.getDouble("${rewardPath}chance", 0.01).coerceIn(0.01, 1.0)
                val name = config.getString("${rewardPath}name") ?: "未知物品"
                val lore = config.getStringList("${rewardPath}lore")
                val materialStr = config.getString("${rewardPath}materials") ?: "STONE"
                val material = Material.getMaterial(materialStr.uppercase()) ?: Material.STONE
                val rewardBroadcast = config.getBoolean("${rewardPath}broadcast", false)
                val commands = config.getStringList("${rewardPath}command")

                rewards.add(FishingReward(chance, name, lore, material, rewardBroadcast, commands))
            }
            rewardPools[poolId] = FishingRewardPool(poolName, enable, broadcast, rewards)
        }
    }

    fun reload() {
        ConfigManager.reloadConfig("rewardpool.yml")
        parseRewardPools(ConfigManager.rewardpoolConfig)
        Customfishing.instance.logger.info("已重载钓鱼奖池配置！")
    }

    fun getEnabledPools(): List<FishingRewardPool> {
        return rewardPools.values.filter { it.enable }
    }

    fun getRandomReward(): FishingReward? {
        val enabledPools = getEnabledPools()
        if (enabledPools.isEmpty()) return null

        val allRewards = enabledPools.flatMap { it.rewards }
        if (allRewards.isEmpty()) return null

        val totalChance = allRewards.sumOf { it.chance }
        val randomValue = Math.random() * totalChance
        var current = 0.0

        for (reward in allRewards) {
            current += reward.chance
            if (randomValue <= current) {
                return reward
            }
        }
        return null
    }
}
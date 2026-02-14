# 🎣CustomFishing插件

## 玩法介绍:
- 这是一款基于Kotlin开发高度自定义的钓鱼插件.
- 可以无限自定义奖池以及奖励
- 自定义钓鱼小游戏
- 支持玩家比赛离线奖励发送至离线仓库
- 支持玩家自建商店.  
- 可视化奖池编辑GUI
- 可视化比赛参与
目前大部分功能暂未完善.


## 提供变量(未完成)
|           变量           |   含义    |
|:----------------------:|:-------:|
|  %fishing_game_比赛名称%   | 钓鱼比赛的名称 |
|   %reward_pool_奖池名称%   | 钓鱼奖池的名称 |
|    %fishing_reward%    | 钓鱼的奖励名称 |
| %fishing_game_rank_排名% | 钓鱼比赛的排名 |
|  %fishing_比赛名_score%   | 玩家比赛的积分 |

## 权限组
| 权限节点                | 作用                        | 默认拥有者 |
|-------------------------|---------------------------|------------|
| customfishing.command   | 允许使用 CustomFishing 插件基础命令 | 所有玩家   |
| customfishing.admin     | 管理员总权限（包含所有子权限）    | OP         |
| customfishing.reload    | 允许重载插件所有配置文件          | OP         |
| customfishing.list      | 允许查看钓鱼奖池配置列表          | OP         |
| customfishing.gui.edit  | 允许编辑插件自定义 GUI 菜单       | OP         |
| customfishing.game      | 允许管理钓鱼小游戏（创建/重置/结束） | OP         |
| customfishing.game.join | 允许加入钓鱼小游戏                | 所有玩家   |
| customfishing.game.quit | 允许主动退出钓鱼小游戏            | 所有玩家   |

## 🚀快速开始

### 使用插件
1. 下载最新Releases的插件本体的jar文件
2. 将jar文件放入服务器/plugin文件夹下
3. 启动服务器成功加载

### 本地构建

1.克隆项目代码到本地
```bash
git clone https://github.com/PMCKkS/CustomFish.git
```
2.进入项目目录
```bash
cd customfishing
```
3.使用gradle构建
```bash
gradlew clean build
```
# 河动力三种大水车验证记录

日期：2026-10-02。项目：River Power-1.20.1-Forge，版本 1.0.0。

## 功能

模组提供 `riverpower:andesite_large_water_wheel`、`riverpower:brass_large_water_wheel` 和 `riverpower:sturdy_large_water_wheel` 三种水车。三者复用 Create 6.0.8 的大水车模型几何、放置空间检测、3×3 占位结构、水流检测、动力网络、串联 extension 状态、扳手回收和 Flywheel/普通方块实体渲染；每种水车使用独立材质并固定外观。

三种水车默认分别提供原版大水车 2 倍、3 倍和 4 倍应力容量，均为 4 RPM。应力容量从 Create 的 `BlockStressValues` 动态读取，整合包修改原版容量后仍按倍率同步计算。

获取方式为手持 Create 安山机壳、黄铜机壳或坚固板，右键原版大水车或其外圈占位方块，消耗对应材料并原地升级；没有额外工作台配方。三种水车都可从“河动力”创造模式分类获取。

## 构建和游戏测试

环境为 Java 17、Forge 47.4.23、Minecraft 1.20.1、Create 6.0.8-289。项目运行时声明 Create 0.0.1 及以上且不设最高版本。`compileJava compileGametestJava` 通过；`runGameTestServer` 运行 8 个测试，日志为 `All 8 required tests passed :)`，Gradle 任务成功。

测试覆盖三种方块实体注册、2/4/8 倍应力容量、Create 占位结构选取、安山水车供能、反向、停转、保存读取、串联、放置扩展和扳手回收。

## 配置

三种水车各有一个独立的 COMMON 配置文件：

- `config/riverpower-andesite_large_water_wheel.toml`
- `config/riverpower-brass_large_water_wheel.toml`
- `config/riverpower-sturdy_large_water_wheel.toml`

每个文件可以控制 `rotationSpeed`、`stressMultiplier` 和 `rightClickUpgrade`。修改转速后重新放置水车或触发方块更新即可生效。

## 资源审计

成品包含三种水车的方块状态、普通/串联 OBJ 包装模型、物品模型、对应贴图、双语语言键、掉落表、矿镐标签、Create `safe_nbt` 标签和 `META-INF/mods.toml`；未嵌入 Create 原版 OBJ/MTL、依赖类或 GameTest 源。

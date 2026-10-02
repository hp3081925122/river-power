# 河动力安山与黄铜大水车验证记录

日期：2026-10-02。项目：River Power-1.20.1-Forge，版本 1.0.0。

## 功能

新增 `riverpower:brass_large_water_wheel`。它复用 Create 6.0.8 的大水车模型几何、放置空间检测、3×3 占位结构、水流检测、4 RPM 生成、动力网络、串联 extension 状态、扳手回收和 Flywheel/普通方块实体渲染。黄铜方块固定使用四张 16×16 黄铜贴图，不响应木板换材质。

黄铜款应力容量从 Create 原版大水车配置动态读取并乘 4；默认是 2048 SU（128 SU/RPM × 4 RPM × 4）。配方是中间安山大水车、周围八个 `create:brass_ingot`。

## 构建和游戏测试

Java 17、Forge 47.4.23、Minecraft 1.20.1、Create 6.0.8-289。`compileJava compileGametestJava processResources` 通过；`runGameTestServer build` 通过，日志为 `7 tests are now running!`、`All 7 required tests passed :)`、`BUILD SUCCESSFUL`。

测试覆盖安山水车原有的供能、反向、停转、保存读取、串联和回收行为，并新增黄铜方块实体注册、Create 占位结构复用和 4 倍配置应力容量检查。

## 客户端启动

使用独立目录 `D:\AI临时文件\river-power-implementation\client-run-brass` 启动，旧客户端没有被关闭。日志确认 River Power 和 Create 6.0.8 加载，声音系统、方块纹理图集和 77 个 Flywheel 着色器初始化完成；没有发现 riverpower 模型缺失报错。该启动检查未替代游戏截图外观检查。

## 资源审计

成品包含黄铜方块状态、普通/串联 OBJ 包装模型、物品模型、四张黄铜贴图、双语语言键、配方、掉落表、矿镐标签、Create safe_nbt 标签和解锁进度；未嵌入 Create 原版 OBJ/MTL、依赖类或 GameTest 源。

成品：`riverpower-1.0.0.jar`
SHA-256：`E8AB2589CE3E3F7699A3F73B9F6566AAC38B0602D78AB0A2F53202389BCF7453`

# 河动力安山大水车验证记录

日期：2026-10-02。项目：River Power-1.20.1-Forge，版本 1.0.0。

## 构建及游戏服务器测试

Java 17、Forge 47.4.23、Minecraft 1.20.1、Create 6.0.8-289。Create 提交号与用户提供的 JAR 一致：`1a1a9a2819b4f89f78caec41b55ed8cb222fa24b`。

`compileJava compileGametestJava` 通过；`runGameTestServer build` 通过，日志显示 `All 6 required tests passed :)` 和 `BUILD SUCCESSFUL in 2m 19s`。

独立 GameTest 世界已验证：

1. 自定义方块实体、八个占位方块、外圈和角落选取安山物品、原版水车选取不变、单物品掉落、破坏外圈后的整轮清理。
2. X 轴轮由气泡柱驱动，生成 4 RPM，带动传动轴；动力网络得到原版大水车 2 倍容量；水流反向后反转，水流移除后停转；方块实体数据保存和读取后流量状态一致。
3. Z 轴轮同样通过上述供能、反向、停转和保存/读取检查。
4. Y 轴轮由水平水流驱动，生成 4 RPM 并向传动轴和动力网络提供正确容量。
5. 使用真实水车物品放置串联轮，extension 状态正确；移除前一轮后正确恢复普通模型状态。
6. 对角落使用扳手回收得到一个安山大水车，整轮占位清理正确。

测试代码和空结构在独立 src/gametest 源集中，未打包进成品 JAR。测试未访问旧开发客户端的世界。

## 客户端

新版客户端使用独立目录 `D:\AI临时文件\river-power-implementation\client-run`，成功加载 River Power 和 Create。2026-10-02 18:01:21 日志确认声音系统、方块纹理图集完成初始化，18:01:22 Flywheel 77 个着色器源码加载完成。未发现 riverpower 模型或贴图缺失报错。

新版客户端 PID 38592，窗口响应正常。旧客户端 PID 33536 仍在单人游戏中，保持运行。

该启动证据证明加载正常；未通过游戏截图验证安山大水车的实际外观。请在新版测试客户端或安装本 JAR 的实例中检查：

- 用 `/give @p riverpower:andesite_large_water_wheel` 获取物品，确认背包、手持、地面及水车三种轴向的安山材质。
- 放置两轮串联，确认接缝、转动和移除前一轮后的模型正常。
- 分别开启和关闭 Flywheel 实例渲染，确认模型、透明区域、转动和光照正常。
- 工作台中用一个 Create 大水车加八个安山合金合成安山大水车；默认有效水流下工程师护目镜应显示 1024 SU、4 RPM。

## 成品审计

入口、注册、方块/物品/方块实体、普通渲染、Flywheel visual、Mixin 类和所有资源齐全；四张自绘贴图均为 16×16。文本资源为 UTF-8，所有 JSON 可解析，中文描述正常。

Manifest 包含 `MixinConfigs: riverpower.mixins.json`。Mixin 成品包含开发和生产双方法名 `getCloneItemStack/m_7397_`、`updateShape/m_7417_`；已核对 Create 6.0.8 的生产 JAR 方法签名。

仅声明 Forge、Minecraft、Create 必需依赖，无可选依赖声明；JAR 未嵌入 Create 原版 OBJ/MTL/轴贴图、依赖类、测试类或结构。

成品：`riverpower-1.0.0.jar`
SHA-256：`B883CCC1DD04679182BD3A1AB3893576F517CB373F40105DD11B9DDB40223E74`

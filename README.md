# River Power（河动力）

Minecraft 1.20.1 Forge 的 Create 水车附属开发项目。当前为项目初始化与模型资源准备阶段。

- 模组 ID：`riverpower`
- Java 包：`com.hp.riverpower`
- Minecraft：1.20.1
- Forge：47.4.23
- Java：17
- Create：6.0.8，开发坐标 `6.0.8-289`，Git 提交与用户提供的 JAR 一致。
- 当前模组版本：1.0.0

## 已完成

基于本机 Forge 模板创建开发环境；配置 Create、Ponder、Flywheel、Registrate 与 MixinExtras；准备安山大水车的普通、串联、物品模型资源和中英语言键；保存可编辑 Blockbench 工程与预览图。

游戏资源直接引用 Create 自带的原版 OBJ，仅覆盖四个材质键。原始几何、轴向、UV 和普通/串联关系保留。

## 当前功能边界

入口类可加载并输出初始化日志。尚未注册安山大水车方块及物品，尚未接入 Create 的方块实体、运行时模型、应力倍率、配方或其他材质等级，因此当前游戏中不会出现新水车。

## 文件位置

- `src/main/java/com/hp/riverpower/RiverPower.java`：模组入口。
- `src/main/resources/assets/riverpower/`：游戏模型、状态、语言与自绘贴图。
- `modeling/andesite_large_water_wheel/`：内嵌贴图的 Blockbench 工程、离线 OBJ/MTL 和模型贴图。
- `docs/`：三视图和旋转预览。

## 构建与运行

```powershell
$env:JAVA_HOME = 'C:\Users\30819\.jdks\ms-17.0.18'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\gradlew.bat build --no-daemon --console=plain
.\gradlew.bat runClient --no-daemon --console=plain
```

## 依赖配置依据

Create 官方开发文档：<https://github.com/Creators-of-Create/wiki/blob/main/src/developers/depend-on-create/forge-1.20.1.md>。Ponder 1.0.91、Flywheel 1.0.5、Registrate MC1.20-1.3.3 与 MixinExtras 0.4.1 已和用户提供 Create JAR 的嵌入元数据核对。

建模参考的原始几何与轴贴图来自 Create 6.0.8；项目运行资源通过 Create 命名空间使用这些资产。原始 OBJ、MTL、轴贴图和转换的 Blockbench 工程仅保留本地参考，已经排除出 Git 与成品 JAR，许可信息见 `THIRD_PARTY_NOTICES.md`。

## 当前验证

2026-10-02：`build` 完成；成品 JAR 的模组元数据、中文描述、JSON 资源与材质文件核对通过，未打包原版几何或原始轴贴图。Create 开发坐标 `6.0.8-289` 的 Git 提交与用户提供的 Create JAR 相同。

开发客户端已完成模组加载、声音系统与纹理图集初始化；Create 日志提交号为 `1a1a9a2819b4f89f78caec41b55ed8cb222fa24b`，River Power 初始化日志存在。客户端保持运行。本次验证不代表新水车玩法或游戏内模型渲染通过，因为方块尚未注册。

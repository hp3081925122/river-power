# River Power（河动力）

Minecraft 1.20.1 Forge 的 Create 水车附属，当前提供安山大水车、黄铜大水车和坚固板大水车。

- 模组 ID：`riverpower`
- Java 包：`com.hp.riverpower`
- Minecraft：1.20.1
- Forge：47.4.23
- Java：17
- Create：开发坐标 `6.0.8-289`，运行时依赖范围为 `0.0.1` 及以上且不设上限。
- 模组版本：1.0.0

## 安山大水车

注册 ID 为 `riverpower:andesite_large_water_wheel`，可从“河动力”创造模式分类获取。获取方式：手持 Create 安山机壳，右键原版大水车（或其外圈占位方块），原地升级为安山大水车，消耗一个安山机壳。没有工作台配方。

沿用 Create 的大水车放置检测、3×3 占位、水流和气泡柱检测、4 RPM 转速、轴向连接、动力网络、眼镜应力提示、串联模型和扳手回收。无有效水流时停止供能；水流反向时反转。安山款材质固定，右键木板不更换外观。

应力容量通过 Create 的 `BlockStressValues` 注册为原版大水车配置值的 2 倍（可在配置文件中修改）。默认原版大水车为 128 SU/RPM × 4 RPM = 512 SU，安山款为 256 SU/RPM × 4 RPM = 1024 SU。整合包修改原版大水车的应力配置后，安山款同步按 2 倍计算。

## 黄铜大水车

注册 ID 为 `riverpower:brass_large_water_wheel`，也在“河动力”创造模式分类中。获取方式：手持 Create 黄铜机壳，右键原版大水车，原地升级为黄铜大水车，消耗一个黄铜机壳。没有工作台配方。

黄铜款复用同一套水流、放置、串联、扳手回收和动态渲染逻辑，固定使用黄铜材质，不接受木板换材质。应力容量为原版大水车配置值的 3 倍，默认 1536 SU，转速仍为 4 RPM。

## 坚固板大水车

注册 ID 为 `riverpower:sturdy_large_water_wheel`，同在“河动力”创造模式分类中。获取方式：手持 Create 坚固板，右键原版大水车，原地升级为坚固板大水车，消耗一个坚固板。没有工作台配方。

坚固板款复用同一套水流、放置、串联、扳手回收和动态渲染逻辑，固定使用坚固板钢色材质，不接受木板换材质。应力容量为原版大水车配置值的 4 倍，默认 2048 SU，转速仍为 4 RPM。方块硬度 4.0、爆炸抗性 8.0，使用下界合金方块音效。

游戏模型引用 Create 原版 OBJ，覆盖自绘材质；普通和串联模型分别接入 Flywheel 实例渲染与普通方块实体渲染，三款水车各自使用对应材质。

Create 6.0.8 的外圈占位方块将选取物品和更新调度写死为原版大水车。本项目通过两个仅作用于本模组三款水车的 Mixin 修正，使外圈/角落的选取物品正确，并及时更新水流。Mixin 使用已核对的 1.20.1 官方映射名和生产环境 SRG 方法名，不需要额外 refmap。

## 配置文件

三种水车各有一个独立的 COMMON 配置文件，位于 `config/` 目录，文件内含中英文注释：

- `riverpower-andesite_large_water_wheel.toml`
- `riverpower-brass_large_water_wheel.toml`
- `riverpower-sturdy_large_water_wheel.toml`

每个文件包含 `rotationSpeed`（转速 RPM，默认 4）、`stressMultiplier`（相对原版大水车的应力倍率，默认安山 2、黄铜 3、坚固板 4）和 `rightClickUpgrade`（是否允许右键原版大水车升级）。

## 构建与运行

```powershell
$env:JAVA_HOME = 'C:\Users\30819\.jdks\ms-17.0.18'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\gradlew.bat build --no-daemon --console=plain
.\gradlew.bat runGameTestServer --no-daemon --console=plain
.\gradlew.bat runClient --no-daemon --console=plain
```

GameTest 代码和结构位于独立的 `src/gametest`，只随 `runGameTestServer` 加载，不打包进成品 JAR。测试使用 `run-gametest` 独立世界，不访问开发客户端的存档。

## 文件位置

- `src/main/java/com/hp/riverpower/`：注册、方块实体、动态渲染及占位兼容。
- `src/main/resources/assets/riverpower/`：模型、状态、语言与自绘贴图。
- `src/main/resources/data/`：掉落表和标签（工作台配方已取消，右键升级逻辑在 `WheelUpgradeHandler`）。
- `src/gametest/`：游戏服务器集成测试。
- `modeling/andesite_large_water_wheel/`：仅供本地编辑的 Blockbench 工程和参考 OBJ/MTL。
- `modeling/*_large_water_wheel/paint_textures.py`：三款水车贴图的字符网格源文件，运行后写回 `src` 贴图目录。
- `docs/`：模型预览和验证记录。

## 资源许可与依赖

Create 官方开发配置依据：<https://github.com/Creators-of-Create/wiki/blob/main/src/developers/depend-on-create/forge-1.20.1.md>。开发和测试使用 Create `6.0.8-289`；运行时依赖声明为 Create `0.0.1` 及以上且不设最高版本。Ponder 1.0.91、Flywheel 1.0.5、Registrate MC1.20-1.3.3、MixinExtras 0.4.1 与开发环境一致。

原始 OBJ、MTL、轴贴图和转换的 Blockbench 工程仅保留本地参考，排除出 Git 和成品 JAR；运行时通过 Create 命名空间引用。详情见 `THIRD_PARTY_NOTICES.md`。

## 验证

本次功能验证结果见 `docs/validation.md`。客户端成功加载与实际游戏内外观验证分别记录。

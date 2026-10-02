# River Power（河动力）

Minecraft 1.20.1 Forge 的 Create 水车附属，当前提供安山大水车和黄铜大水车。

- 模组 ID：`riverpower`
- Java 包：`com.hp.riverpower`
- Minecraft：1.20.1
- Forge：47.4.23
- Java：17
- Create：6.0.8，开发坐标 `6.0.8-289`，提交号 `1a1a9a2819b4f89f78caec41b55ed8cb222fa24b`，与用户提供的 JAR 一致。
- 模组版本：1.0.0

## 安山大水车

注册 ID 为 `riverpower:andesite_large_water_wheel`，可从“河动力”创造模式分类获取。工作台配方：中间放一个 Create 大水车，周围放八个安山合金。

沿用 Create 的大水车放置检测、3×3 占位、水流和气泡柱检测、4 RPM 转速、轴向连接、动力网络、眼镜应力提示、串联模型和扳手回收。无有效水流时停止供能；水流反向时反转。安山款材质固定，右键木板不更换外观。

应力容量通过 Create 的 `BlockStressValues` 注册为原版大水车配置值的 2 倍，不改变转速。默认原版大水车为 128 SU/RPM × 4 RPM = 512 SU，安山款为 256 SU/RPM × 4 RPM = 1024 SU。整合包修改原版大水车的应力配置后，安山款同步按 2 倍计算。

## 黄铜大水车

注册 ID 为 `riverpower:brass_large_water_wheel`，也在“河动力”创造模式分类中。工作台配方：中间放安山大水车，周围放八个 Create 黄铜锭。

黄铜款复用同一套水流、放置、串联、扳手回收和动态渲染逻辑，固定使用黄铜材质，不接受木板换材质。应力容量为原版大水车配置值的 4 倍，默认 2048 SU，转速仍为 4 RPM。

游戏模型引用 Create 原版 OBJ，覆盖自绘材质；普通和串联模型分别接入 Flywheel 实例渲染与普通方块实体渲染。两种渲染都使用同一套安山材质。

Create 6.0.8 的外圈占位方块将选取物品和更新调度写死为原版大水车。本项目通过两个仅作用于安山款的 Mixin 修正，使外圈/角落的选取物品正确，并及时更新水流。Mixin 使用已核对的 1.20.1 官方映射名和生产环境 SRG 方法名，不需要额外 refmap。

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
- `src/main/resources/data/`：工作台配方、解锁进度、掉落表和标签。
- `src/gametest/`：游戏服务器集成测试。
- `modeling/andesite_large_water_wheel/`：仅供本地编辑的 Blockbench 工程和参考 OBJ/MTL。
- `docs/`：模型预览和验证记录。

## 资源许可与依赖

Create 官方开发配置依据：<https://github.com/Creators-of-Create/wiki/blob/main/src/developers/depend-on-create/forge-1.20.1.md>。Ponder 1.0.91、Flywheel 1.0.5、Registrate MC1.20-1.3.3、MixinExtras 0.4.1 与用户提供 JAR 的元数据一致。

原始 OBJ、MTL、轴贴图和转换的 Blockbench 工程仅保留本地参考，排除出 Git 和成品 JAR；运行时通过 Create 命名空间引用。详情见 `THIRD_PARTY_NOTICES.md`。

## 验证

本次功能验证结果见 `docs/validation.md`。客户端成功加载与实际游戏内外观验证分别记录。

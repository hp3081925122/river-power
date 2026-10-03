package com.hp.riverpower;

import com.hp.riverpower.client.AndesiteWaterWheelRenderer;
import com.hp.riverpower.client.AndesiteWaterWheelVisual;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlock;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockItem;
import com.hp.riverpower.content.BrassLargeWaterWheelBlock;
import com.hp.riverpower.content.BrassLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.BrassLargeWaterWheelBlockItem;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlock;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlockItem;
import com.hp.riverpower.client.BrassWaterWheelRenderer;
import com.hp.riverpower.client.BrassWaterWheelVisual;
import com.hp.riverpower.client.SturdyWaterWheelRenderer;
import com.hp.riverpower.client.SturdyWaterWheelVisual;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class RiverPowerContent {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RiverPower.MOD_ID);

    public static final BlockEntry<AndesiteLargeWaterWheelBlock> ANDESITE_LARGE_WATER_WHEEL =
            RiverPower.REGISTRATE.block("andesite_large_water_wheel", AndesiteLargeWaterWheelBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
                    .properties(properties -> properties.noOcclusion().sound(SoundType.METAL).strength(3.0f))
                    .onRegister(block -> BlockStressValues.CAPACITIES.register(block,
                            () -> BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get())
                                    * RiverPowerConfig.ANDESITE.stressMultiplier()))
                    .onRegister(block -> BlockStressValues.RPM.registerProvider(RiverPowerConfig.ANDESITE.rpmProvider(block)))
                    .item(AndesiteLargeWaterWheelBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntityEntry<AndesiteLargeWaterWheelBlockEntity> ANDESITE_LARGE_WATER_WHEEL_ENTITY =
            RiverPower.REGISTRATE.blockEntity("andesite_large_water_wheel", AndesiteLargeWaterWheelBlockEntity::new)
                    .visual(() -> AndesiteWaterWheelVisual::new, false)
                    .validBlocks(ANDESITE_LARGE_WATER_WHEEL)
                    .renderer(() -> AndesiteWaterWheelRenderer::new)
                    .register();

    public static final BlockEntry<BrassLargeWaterWheelBlock> BRASS_LARGE_WATER_WHEEL =
            RiverPower.REGISTRATE.block("brass_large_water_wheel", BrassLargeWaterWheelBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
                    .properties(properties -> properties.noOcclusion().sound(SoundType.METAL).strength(3.5f))
                    .onRegister(block -> BlockStressValues.CAPACITIES.register(block,
                            () -> BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get())
                                    * RiverPowerConfig.BRASS.stressMultiplier()))
                    .onRegister(block -> BlockStressValues.RPM.registerProvider(RiverPowerConfig.BRASS.rpmProvider(block)))
                    .item(BrassLargeWaterWheelBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntityEntry<BrassLargeWaterWheelBlockEntity> BRASS_LARGE_WATER_WHEEL_ENTITY =
            RiverPower.REGISTRATE.blockEntity("brass_large_water_wheel", BrassLargeWaterWheelBlockEntity::new)
                    .visual(() -> BrassWaterWheelVisual::new, false)
                    .validBlocks(BRASS_LARGE_WATER_WHEEL)
                    .renderer(() -> BrassWaterWheelRenderer::new)
                    .register();

    public static final BlockEntry<SturdyLargeWaterWheelBlock> STURDY_LARGE_WATER_WHEEL =
            RiverPower.REGISTRATE.block("sturdy_large_water_wheel", SturdyLargeWaterWheelBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
                    .properties(properties -> properties.noOcclusion().sound(SoundType.NETHERITE_BLOCK).strength(4.0f, 8.0f))
                    .onRegister(block -> BlockStressValues.CAPACITIES.register(block,
                            () -> BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get())
                                    * RiverPowerConfig.STURDY.stressMultiplier()))
                    .onRegister(block -> BlockStressValues.RPM.registerProvider(RiverPowerConfig.STURDY.rpmProvider(block)))
                    .item(SturdyLargeWaterWheelBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntityEntry<SturdyLargeWaterWheelBlockEntity> STURDY_LARGE_WATER_WHEEL_ENTITY =
            RiverPower.REGISTRATE.blockEntity("sturdy_large_water_wheel", SturdyLargeWaterWheelBlockEntity::new)
                    .visual(() -> SturdyWaterWheelVisual::new, false)
                    .validBlocks(STURDY_LARGE_WATER_WHEEL)
                    .renderer(() -> SturdyWaterWheelRenderer::new)
                    .register();

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RIVER_POWER_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.riverpower"))
                    .icon(ANDESITE_LARGE_WATER_WHEEL::asStack)
                    .build());

    private RiverPowerContent() {
    }

    public static void register(IEventBus modBus) {
        modBus.addListener(RiverPowerContent::buildTabContents);
        TABS.register(modBus);
    }

    private static void buildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(RIVER_POWER_TAB.getKey())) {
            return;
        }
        if (event.getParentEntries().stream().noneMatch(stack -> stack.is(ANDESITE_LARGE_WATER_WHEEL.asItem()))) {
            event.accept(ANDESITE_LARGE_WATER_WHEEL.get());
        }
        if (event.getParentEntries().stream().noneMatch(stack -> stack.is(BRASS_LARGE_WATER_WHEEL.asItem()))) {
            event.accept(BRASS_LARGE_WATER_WHEEL.get());
        }
        if (event.getParentEntries().stream().noneMatch(stack -> stack.is(STURDY_LARGE_WATER_WHEEL.asItem()))) {
            event.accept(STURDY_LARGE_WATER_WHEEL.get());
        }
    }
}

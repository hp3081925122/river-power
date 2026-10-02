package com.hp.riverpower;

import com.hp.riverpower.client.AndesiteWaterWheelRenderer;
import com.hp.riverpower.client.AndesiteWaterWheelVisual;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlock;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockEntity;
import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockItem;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class RiverPowerContent {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RiverPower.MOD_ID);

    public static final BlockEntry<AndesiteLargeWaterWheelBlock> ANDESITE_LARGE_WATER_WHEEL =
            RiverPower.REGISTRATE.block("andesite_large_water_wheel", AndesiteLargeWaterWheelBlock::new)
                    .initialProperties(() -> Blocks.ANDESITE)
                    .properties(properties -> properties.noOcclusion().sound(SoundType.METAL).strength(3.0f))
                    .onRegister(block -> BlockStressValues.CAPACITIES.register(block,
                            () -> BlockStressValues.getCapacity(AllBlocks.LARGE_WATER_WHEEL.get()) * 2))
                    .onRegister(BlockStressValues.setGeneratorSpeed(4))
                    .item(AndesiteLargeWaterWheelBlockItem::new)
                    .build()
                    .register();

    public static final BlockEntityEntry<AndesiteLargeWaterWheelBlockEntity> ANDESITE_LARGE_WATER_WHEEL_ENTITY =
            RiverPower.REGISTRATE.blockEntity("andesite_large_water_wheel", AndesiteLargeWaterWheelBlockEntity::new)
                    .visual(() -> AndesiteWaterWheelVisual::new, false)
                    .validBlocks(ANDESITE_LARGE_WATER_WHEEL)
                    .renderer(() -> AndesiteWaterWheelRenderer::new)
                    .register();

    public static final RegistryObject<CreativeModeTab> RIVER_POWER_TAB = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.riverpower"))
                    .icon(ANDESITE_LARGE_WATER_WHEEL::asStack)
                    .displayItems((parameters, output) -> output.accept(ANDESITE_LARGE_WATER_WHEEL.get()))
                    .build());

    private RiverPowerContent() {
    }

    public static void register(IEventBus modBus) {
        TABS.register(modBus);
    }
}

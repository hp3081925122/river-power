package com.hp.riverpower.client;

import com.hp.riverpower.RiverPower;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public final class RiverPowerModels {
    private static final PartialModel WHEEL = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath(RiverPower.MOD_ID, "block/andesite_large_water_wheel/block"));
    private static final PartialModel EXTENSION = PartialModel.of(
            ResourceLocation.fromNamespaceAndPath(RiverPower.MOD_ID, "block/andesite_large_water_wheel/block_extension"));

    private RiverPowerModels() {
    }

    public static void initialize() {
    }

    public static PartialModel wheel(BlockState state) {
        return state.getValue(LargeWaterWheelBlock.EXTENSION) ? EXTENSION : WHEEL;
    }
}

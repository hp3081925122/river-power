package com.hp.riverpower.content;

import com.hp.riverpower.RiverPowerConfig;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public final class BrassLargeWaterWheelBlockEntity extends LargeWaterWheelBlockEntity {
    public BrassLargeWaterWheelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public InteractionResult applyMaterialIfValid(ItemStack stack) {
        return InteractionResult.PASS;
    }

    @Override
    public float getGeneratedSpeed() {
        return Mth.clamp(flowScore, -1, 1) * RiverPowerConfig.BRASS.rpm();
    }
}

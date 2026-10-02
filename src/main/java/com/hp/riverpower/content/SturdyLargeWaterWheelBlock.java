package com.hp.riverpower.content;

import com.hp.riverpower.RiverPowerContent;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class SturdyLargeWaterWheelBlock extends LargeWaterWheelBlock {
    public SturdyLargeWaterWheelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends LargeWaterWheelBlockEntity> getBlockEntityType() {
        return RiverPowerContent.STURDY_LARGE_WATER_WHEEL_ENTITY.get();
    }
}

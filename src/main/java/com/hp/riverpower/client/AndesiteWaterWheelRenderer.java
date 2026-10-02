package com.hp.riverpower.client;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlock;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public final class AndesiteWaterWheelRenderer extends KineticBlockEntityRenderer<AndesiteLargeWaterWheelBlockEntity> {
    public AndesiteWaterWheelRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(AndesiteLargeWaterWheelBlockEntity entity, BlockState state) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, state.getValue(LargeWaterWheelBlock.AXIS));
        return CachedBuffers.partialFacingVertical(RiverPowerModels.wheel(state), state, direction);
    }
}

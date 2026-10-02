package com.hp.riverpower.mixin;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WaterWheelStructuralBlock.class, remap = false)
public abstract class WaterWheelStructuralBlockMixin {
    @Inject(method = {"getCloneItemStack", "m_7397_"}, at = @At("HEAD"), cancellable = true)
    private void riverpower$pickWheel(BlockGetter level, BlockPos pos, BlockState state,
                                     CallbackInfoReturnable<ItemStack> callback) {
        if (!AllBlocks.WATER_WHEEL_STRUCTURAL.get().stillValid(level, pos, state, false)) {
            return;
        }
        BlockState master = level.getBlockState(WaterWheelStructuralBlock.getMaster(level, pos, state));
        if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock) {
            callback.setReturnValue(new ItemStack(master.getBlock()));
        }
    }

    @Inject(method = {"updateShape", "m_7417_"}, at = @At("HEAD"), cancellable = true)
    private void riverpower$updateWheel(BlockState state, Direction direction, BlockState neighbor,
                                       LevelAccessor level, BlockPos pos, BlockPos neighborPos,
                                       CallbackInfoReturnable<BlockState> callback) {
        if (!AllBlocks.WATER_WHEEL_STRUCTURAL.get().stillValid(level, pos, state, false)) {
            return;
        }
        BlockPos masterPos = WaterWheelStructuralBlock.getMaster(level, pos, state);
        BlockState master = level.getBlockState(masterPos);
        if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock wheel) {
            if (!level.getBlockTicks().hasScheduledTick(masterPos, wheel)) {
                level.scheduleTick(masterPos, wheel, 1);
            }
            callback.setReturnValue(state);
        }
    }
}

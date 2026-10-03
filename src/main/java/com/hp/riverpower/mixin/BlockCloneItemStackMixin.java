package com.hp.riverpower.mixin;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlock;
import com.hp.riverpower.content.BrassLargeWaterWheelBlock;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlock;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock;
import com.simibubi.create.AllBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockCloneItemStackMixin {
    @Inject(method = "getCloneItemStack", at = @At("HEAD"), cancellable = true)
    private void riverpower$pickWheel(LevelReader level, BlockPos pos, BlockState state,
                                      CallbackInfoReturnable<ItemStack> callback) {
        if (!(state.getBlock() instanceof WaterWheelStructuralBlock)) {
            return;
        }
        BlockState master = level.getBlockState(WaterWheelStructuralBlock.getMaster(level, pos, state));
        if (master.is(AllBlocks.LARGE_WATER_WHEEL.get())) {
            callback.setReturnValue(AllBlocks.LARGE_WATER_WHEEL.asStack());
        } else if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock
                || master.getBlock() instanceof BrassLargeWaterWheelBlock
                || master.getBlock() instanceof SturdyLargeWaterWheelBlock) {
            callback.setReturnValue(new ItemStack(master.getBlock()));
        }
    }
}

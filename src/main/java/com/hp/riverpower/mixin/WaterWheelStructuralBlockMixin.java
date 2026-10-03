package com.hp.riverpower.mixin;

import com.hp.riverpower.content.AndesiteLargeWaterWheelBlock;
import com.hp.riverpower.content.BrassLargeWaterWheelBlock;
import com.hp.riverpower.content.SturdyLargeWaterWheelBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WaterWheelStructuralBlock.class, remap = false)
public abstract class WaterWheelStructuralBlockMixin {
    @Inject(method = "getCloneItemStack", at = @At("HEAD"), cancellable = true)
    private void riverpower$pickWheel(BlockState state, HitResult hit, LevelReader level, BlockPos pos, Player player,
                                     CallbackInfoReturnable<ItemStack> callback) {
        BlockState master = level.getBlockState(WaterWheelStructuralBlock.getMaster(level, pos, state));
        if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock
                || master.getBlock() instanceof BrassLargeWaterWheelBlock
                || master.getBlock() instanceof SturdyLargeWaterWheelBlock) {
            callback.setReturnValue(new ItemStack(master.getBlock()));
        }
    }

    @Inject(method = "onSneakWrenched", at = @At("HEAD"), cancellable = true)
    private void riverpower$wrenchWheel(BlockState state, UseOnContext context,
                                        CallbackInfoReturnable<InteractionResult> callback) {
        BlockPos masterPos = WaterWheelStructuralBlock.getMaster(context.getLevel(), context.getClickedPos(), state);
        BlockState master = context.getLevel().getBlockState(masterPos);
        if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock
                || master.getBlock() instanceof BrassLargeWaterWheelBlock
                || master.getBlock() instanceof SturdyLargeWaterWheelBlock) {
            if (!context.getLevel().isClientSide) {
                context.getLevel().destroyBlock(masterPos, false);
                context.getPlayer().getInventory().placeItemBackInInventory(new ItemStack(master.getBlock()));
            }
            callback.setReturnValue(InteractionResult.sidedSuccess(context.getLevel().isClientSide));
        }
    }


    @Inject(method = "updateShape", at = @At("HEAD"), cancellable = true)
    private void riverpower$updateWheel(BlockState state, Direction direction, BlockState neighbor,
                                       LevelAccessor level, BlockPos pos, BlockPos neighborPos,
                                       CallbackInfoReturnable<BlockState> callback) {
        BlockPos masterPos = WaterWheelStructuralBlock.getMaster(level, pos, state);
        BlockState master = level.getBlockState(masterPos);
        if (master.getBlock() instanceof AndesiteLargeWaterWheelBlock
                || master.getBlock() instanceof BrassLargeWaterWheelBlock
                || master.getBlock() instanceof SturdyLargeWaterWheelBlock) {
            var wheel = master.getBlock();
            if (!level.getBlockTicks().hasScheduledTick(masterPos, wheel)) {
                level.scheduleTick(masterPos, wheel, 1);
            }
            callback.setReturnValue(state);
        }
    }
}

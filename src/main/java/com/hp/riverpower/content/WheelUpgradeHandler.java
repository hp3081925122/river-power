package com.hp.riverpower.content;

import com.hp.riverpower.RiverPower;
import com.hp.riverpower.RiverPowerConfig;
import com.hp.riverpower.RiverPowerContent;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = RiverPower.MOD_ID)
public final class WheelUpgradeHandler {
    private WheelUpgradeHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }
        ItemStack stack = event.getItemStack();
        Block target = targetFor(stack);
        if (target == null) {
            return;
        }
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState clicked = level.getBlockState(pos);
        BlockPos masterPos;
        if (clicked.is(AllBlocks.LARGE_WATER_WHEEL.get())) {
            masterPos = pos;
        } else if (clicked.is(AllBlocks.WATER_WHEEL_STRUCTURAL.get())) {
            masterPos = WaterWheelStructuralBlock.getMaster(level, pos, clicked);
        } else {
            return;
        }
        BlockState master = level.getBlockState(masterPos);
        if (!master.is(AllBlocks.LARGE_WATER_WHEEL.get())) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide));
        if (level.isClientSide) {
            return;
        }
        level.setBlock(masterPos, copyProperties(master, target.defaultBlockState()), Block.UPDATE_ALL);
        level.playSound(null, masterPos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 0.6f, 1.2f);
        if (!event.getEntity().getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    private static Block targetFor(ItemStack stack) {
        if (stack.is(AllBlocks.ANDESITE_CASING.asItem()) && RiverPowerConfig.ANDESITE.upgradeEnabled()) {
            return RiverPowerContent.ANDESITE_LARGE_WATER_WHEEL.get();
        }
        if (stack.is(AllBlocks.BRASS_CASING.asItem()) && RiverPowerConfig.BRASS.upgradeEnabled()) {
            return RiverPowerContent.BRASS_LARGE_WATER_WHEEL.get();
        }
        if (stack.is(com.simibubi.create.AllItems.STURDY_SHEET.get()) && RiverPowerConfig.STURDY.upgradeEnabled()) {
            return RiverPowerContent.STURDY_LARGE_WATER_WHEEL.get();
        }
        return null;
    }

    private static BlockState copyProperties(BlockState from, BlockState to) {
        for (Property<?> property : from.getProperties()) {
            if (to.hasProperty(property)) {
                to = copyProperty(from, to, property);
            }
        }
        return to;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }
}

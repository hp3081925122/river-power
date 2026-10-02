package com.hp.riverpower.content;

import com.simibubi.create.content.kinetics.waterwheel.LargeWaterWheelBlockItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public final class BrassLargeWaterWheelBlockItem extends LargeWaterWheelBlockItem {
    public BrassLargeWaterWheelBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.riverpower.brass_large_water_wheel")
                .withStyle(ChatFormatting.GOLD));
    }
}

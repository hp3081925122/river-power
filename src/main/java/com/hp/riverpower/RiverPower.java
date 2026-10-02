package com.hp.riverpower;

import com.mojang.logging.LogUtils;
import com.hp.riverpower.client.RiverPowerModels;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RiverPower.MOD_ID)
public final class RiverPower {
    public static final String MOD_ID = "riverpower";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item -> TooltipModifier.mapNull(KineticStats.create(item)));
    private static final Logger LOGGER = LogUtils.getLogger();

    public RiverPower(FMLJavaModLoadingContext context) {
        var modBus = context.getModEventBus();
        RiverPowerConfig.register(context);
        REGISTRATE.registerEventListeners(modBus);
        RiverPowerContent.register(modBus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> RiverPowerModels::initialize);
        LOGGER.info("River Power initialized: andesite, brass and sturdy large water wheels, speed and stress are configurable.");
    }
}

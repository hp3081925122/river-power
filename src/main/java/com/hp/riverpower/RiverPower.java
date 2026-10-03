package com.hp.riverpower;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;

@Mod(RiverPower.MOD_ID)
public final class RiverPower {
    public static final String MOD_ID = "riverpower";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .setTooltipModifierFactory(item -> TooltipModifier.mapNull(KineticStats.create(item)));
    private static final Logger LOGGER = LogUtils.getLogger();

    public RiverPower(IEventBus modBus, ModContainer modContainer) {
        RiverPowerConfig.register(modContainer);
        REGISTRATE.registerEventListeners(modBus);
        RiverPowerContent.register(modBus);
        LOGGER.info("River Power initialized: andesite, brass and sturdy large water wheels, speed and stress are configurable.");
    }
}

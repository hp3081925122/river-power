package com.hp.riverpower;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(RiverPower.MOD_ID)
public final class RiverPower {
    public static final String MOD_ID = "riverpower";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RiverPower(FMLJavaModLoadingContext context) {
        LOGGER.info("River Power bootstrap initialized. Content registration is pending.");
    }
}

package com.cute_dragon_project.xingxing.pet_dragon_procedure;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.events.TouchDragonEvent;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("pet_dragon_procedure")
public class PetDragonProcedureMod {

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "pet_dragon_procedure";
    public static final boolean IS_DEBUG = false;
    public PetDragonProcedureMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TouchDragonEvent());
    }
}

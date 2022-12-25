package com.cute_dragon_project.xingxing.pet_dragon_procedure.messages;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.PetDragonProcedureMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MessageRegistry {
    public static final String MESSAGE_VERSION = "1";
    public static SimpleChannel MESSAGE_HANDLER = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(PetDragonProcedureMod.MOD_ID, PetDragonProcedureMod.MOD_ID),
        ()->MESSAGE_VERSION, MESSAGE_VERSION::equals, MESSAGE_VERSION::equals
    );
}

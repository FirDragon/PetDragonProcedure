package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.PetDragonProcedureMod;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.Entity;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber
public class TouchDragon {
    private static Method isDragonMethod = null;
    public TouchDragon()
    {
        try {
            Class<?> DragonUtils = Class.forName("by.dragonsurvivalteam.dragonsurvival.util.DragonUtils");
            isDragonMethod = DragonUtils.getMethod("isDragon", Entity.class);
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
        }
    }
    public static boolean isDragon(Entity entity){
        try {
            return (boolean)isDragonMethod.invoke(null, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
    @SubscribeEvent
    void onRightClickEntity(PlayerInteractEvent.EntityInteract event)
    {
        if (isDragonMethod == null)
            return;
        Player source = event.getPlayer();
        if (!source.getMainHandItem().isEmpty() ||
            !(event.getTarget() instanceof ServerPlayer target) ||
            !isDragon(target)
        )
            return;
        source.swing(InteractionHand.MAIN_HAND);
        if (Math.random() < 0.5F)
        {
            target.displayClientMessage(new TextComponent(String.format("[%s]摸了摸你", source.getName())), true);
            source.displayClientMessage(new TextComponent(String.format("你摸了摸[%s]，被咬了一口", "target.getName()")), true);
        }else{
            target.displayClientMessage(new TextComponent(String.format("[%s]摸了摸你，感觉很舒适", source.getName())), true);
            source.displayClientMessage(new TextComponent(String.format("你摸了摸[%s]，感觉很舒适", "target.getName()")), true);
        }
    }
}

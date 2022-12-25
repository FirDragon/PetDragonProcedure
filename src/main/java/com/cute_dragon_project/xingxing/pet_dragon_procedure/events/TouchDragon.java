package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod.EventBusSubscriber
public class TouchDragon {
    private Field dummyDragon = null;
    private static Method isDragonMethod = null;
    Field biteAnimationControllerField = null;
    public TouchDragon()
    {
        try {
            Class<?> DragonUtils = Class.forName("by.dragonsurvivalteam.dragonsurvival.util.DragonUtils");
            Class<?> DragonEntity = Class.forName("by.dragonsurvivalteam.dragonsurvival.common.entity.DragonEntity");
            Class<?> ClientDragonRender = Class.forName("by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender");
            isDragonMethod = DragonUtils.getMethod("isDragon", Entity.class);
            biteAnimationControllerField = DragonEntity.getDeclaredField("biteAnimationController");
            biteAnimationControllerField.setAccessible(true);
            dummyDragon = ClientDragonRender.getDeclaredField("dummyDragon");
            dummyDragon.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException ignored) {
        }
    }
    public static boolean isDragon(Entity entity){
        try {
            return (boolean)isDragonMethod.invoke(null, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
    private void swing(Player player)
    {
        player.swing(InteractionHand.MAIN_HAND);
//        if (dummyDragon == null)
//            return;
//        try {
//            Object dragonEntity = dummyDragon.get(null);
//            Object biteAnimationController = biteAnimationControllerField.get(dragonEntity);
//            AnimationController controller = (AnimationController)biteAnimationController;
//            AnimationBuilder animationBuilder = new AnimationBuilder();
//            animationBuilder.addAnimation("use_item_left", ILoopType.EDefaultLoopTypes.LOOP);
//            controller.setAnimation(animationBuilder);
//            player.displayClientMessage(new TextComponent("IS OK~"), true);
//        } catch (IllegalAccessException ignored) {
//            player.displayClientMessage(new TextComponent(ignored.getMessage()), true);
//        }
    }
    private void TouchParticles(SimpleParticleType particleType, ServerLevel level,ServerPlayer player)
    {
        Vec3 position = player.getPosition(0.0F);
        level.sendParticles(particleType, position.x, position.y, position.z, 20,1,2,1,1);
    }
    @SubscribeEvent
    void onRightClickEntity(PlayerInteractEvent.EntityInteract event)
    {

        if (isDragonMethod == null)
            return;
        Player source = event.getPlayer();
        if (event.getWorld().isClientSide())
        {
            swing(source);
            return;
        }
        if (!source.getMainHandItem().isEmpty() ||
            !(event.getTarget() instanceof ServerPlayer target) ||
            !isDragon(target)
        )
            return;
        if (Math.random() < 0.5F)
        {
            target.displayClientMessage(new TextComponent(String.format("[%s]\u6478\u4e86\u6478\u4f60", source.getName().getString())), true);
            source.displayClientMessage(new TextComponent(String.format("\u4f60\u6478\u4e86\u6478[%s]\uff0c\u88ab\u54ac\u4e86\u4e00\u53e3", target.getName().getString())), true);
            TouchParticles(ParticleTypes.LARGE_SMOKE, (ServerLevel)event.getWorld(), target);
        }else{
            target.displayClientMessage(new TextComponent(String.format("[%s]\u6478\u4e86\u6478\u4f60\uff0c\u611f\u89c9\u5f88\u8212\u9002", source.getName().getString())), true);
            source.displayClientMessage(new TextComponent(String.format("\u4f60\u6478\u4e86\u6478[%s]\uff0c\u611f\u89c9\u5f88\u8212\u9002", target.getName().getString())), true);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 10, 1));
            TouchParticles(ParticleTypes.HEART, (ServerLevel)event.getWorld(), target);
        }
    }
}

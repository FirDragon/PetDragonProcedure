package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.TranslatableComponent;
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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.cute_dragon_project.xingxing.pet_dragon_procedure.PetDragonProcedureMod.IS_DEBUG;

@Mod.EventBusSubscriber()
public class TouchDragonEvent {
    private static final Field dummyDragon = null;
    private static Method isDragonMethod = null;
    Field biteAnimationControllerField = null;
    public TouchDragonEvent()
    {
        try {
            Class<?> DragonUtils = Class.forName("by.dragonsurvivalteam.dragonsurvival.util.DragonUtils");
//            Class<?> DragonEntity = Class.forName("by.dragonsurvivalteam.dragonsurvival.common.entity.DragonEntity");
//            Class<?> ClientDragonRender = Class.forName("by.dragonsurvivalteam.dragonsurvival.client.render.ClientDragonRender");
            isDragonMethod = DragonUtils.getMethod("isDragon", Entity.class);
//            biteAnimationControllerField = DragonEntity.getDeclaredField("biteAnimationController");
//            biteAnimationControllerField.setAccessible(true);
//            dummyDragon = ClientDragonRender.getDeclaredField("dummyDragon");
//            dummyDragon.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
        }
    }
    public static boolean isDragon(Entity entity){
        if (isDragonMethod == null)
            return false;
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
        level.sendParticles(particleType, player.getX(), player.getY(), player.getZ(), 20,1,2,1,1);
    }
    @SubscribeEvent
    void onRightClickEntity(PlayerInteractEvent.EntityInteract event)
    {
        Player source = event.getPlayer();
        if (event.getWorld().isClientSide())
        {
            swing(source);
            return;
        }
        if (IS_DEBUG)
        {
            Vec3 position = event.getPlayer().getPosition(0.0F);
            if (event.getWorld() instanceof ServerLevel level)
                TouchParticles(ParticleTypes.HEART, (ServerLevel)event.getWorld(), (ServerPlayer) event.getPlayer());
        }
        if (!source.getMainHandItem().isEmpty() ||
            !(event.getTarget() instanceof ServerPlayer target) ||
            !isDragon(target)
        )
            return;
        if (Math.random() < 0.5F)
        {
            target.displayClientMessage(new TranslatableComponent("text.touch_you", source.getName().getString()), true);
            source.displayClientMessage(new TranslatableComponent("text.touch_got_bitten", target.getName().getString()), true);
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 10, 0));
            TouchParticles(ParticleTypes.LARGE_SMOKE, (ServerLevel)event.getWorld(), target);
        }else{
            target.displayClientMessage(new TranslatableComponent("text.touch_felt_comfortable", source.getName().getString()), true);
            source.displayClientMessage(new TranslatableComponent("text.you_felt_comfortable", target.getName().getString()), true);
            target.addEffect(new MobEffectInstance(MobEffects.LUCK, 100, 0));
            TouchParticles(ParticleTypes.HEART, (ServerLevel)event.getWorld(), target);
        }
    }
}

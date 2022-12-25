package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class SensingNearbyTeammatesEvent {
	public static void execute(Player player) {
		if (!(player instanceof ServerPlayer serverPlayer))
			return;

		serverPlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 10 * 40, 1));
		serverPlayer.displayClientMessage(new TranslatableComponent("text.sensing_nearby_teammates"), (true));
	}
}

package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.TextComponent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HungerwarnProcedure {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) <= 4) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
				_player.displayClientMessage(new TranslatableComponent("text.you_are_very_hungry"), (true));
		}
	}
}

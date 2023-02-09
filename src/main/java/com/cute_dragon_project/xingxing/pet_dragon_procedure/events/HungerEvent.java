package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HungerEvent {
	private static final Style RED_BOLD_STYLE = Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(0xff0000));
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			execute(event, event.player);
		}
	}
	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) <= 4) {
			if (entity instanceof Player _player && !_player.level.isClientSide())
			{
				TranslatableComponent showText = new TranslatableComponent("text.you_are_very_hungry");
				showText.setStyle(RED_BOLD_STYLE);
				_player.displayClientMessage(showText, (true));
			}
		}
	}
}

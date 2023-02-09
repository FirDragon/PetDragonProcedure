package com.cute_dragon_project.xingxing.pet_dragon_procedure;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class SensingNearbyTeammates {
	private static final Style GREEN_BOLD_STYLE = Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(0x02CA02));
	public static void execute(Player player) {
		if (!(player instanceof ServerPlayer serverPlayer))
			return;

		TranslatableComponent translatableComponent = new TranslatableComponent("text.sensing_nearby_teammates");
		translatableComponent.setStyle(GREEN_BOLD_STYLE);
		serverPlayer.displayClientMessage(translatableComponent, (true));
		List<ServerPlayer> inRangePlayers = serverPlayer.getLevel().getPlayers(
			filterPlayer -> (filterPlayer.position().distanceTo(player.position()) < 100)
		);
		for (ServerPlayer inRangePlayer : inRangePlayers)
			inRangePlayer.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4 * 20, 0, false, false, false));
	}
}

package com.cute_dragon_project.xingxing.pet_dragon_procedure.messages;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.SensingNearbyTeammates;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.cute_dragon_project.xingxing.pet_dragon_procedure.messages.MessageRegistry.MESSAGE_HANDLER;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SensingNearbyTeammatesMessage {
    int type, pressedMS;
    public SensingNearbyTeammatesMessage(int type, int pressedMS) {
        this.type = type;
        this.pressedMS = pressedMS;
    }
    public static SensingNearbyTeammatesMessage decode(FriendlyByteBuf buffer) {
        return new SensingNearbyTeammatesMessage(
            buffer.readInt(),buffer.readInt()
        );
    }
    public static void encode(SensingNearbyTeammatesMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedMS);
    }
    public static void consumer(SensingNearbyTeammatesMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), message.type, message.pressedMS);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedMS) {
        if (type == 0) {
            SensingNearbyTeammates.execute(entity);
        }
    }
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MESSAGE_HANDLER.registerMessage(
            0,
            SensingNearbyTeammatesMessage.class,
            SensingNearbyTeammatesMessage::encode,
            SensingNearbyTeammatesMessage::decode,
            SensingNearbyTeammatesMessage::consumer
        );
    }
}

package com.cute_dragon_project.xingxing.pet_dragon_procedure.messages;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.events.SensingNearbyTeammatesEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.cute_dragon_project.xingxing.pet_dragon_procedure.messages.MessageRegistry.MESSAGE_HANDLER;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SensingNearbyTeammatesKeyEvent {
    int type, pressedms;
    public SensingNearbyTeammatesKeyEvent(int type, int pressedms) {
        this.type = type;
        this.pressedms = pressedms;
    }
    public static SensingNearbyTeammatesKeyEvent encode(FriendlyByteBuf buffer) {
        return new SensingNearbyTeammatesKeyEvent(
            buffer.readInt(),buffer.readInt()
        );
    }
    public static void decode(SensingNearbyTeammatesKeyEvent message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedms);
    }
    public static void handler(SensingNearbyTeammatesKeyEvent message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            pressAction(context.getSender(), message.type, message.pressedms);
        });
        context.setPacketHandled(true);
    }

    public static void pressAction(Player entity, int type, int pressedms) {
        Level world = entity.level;
        // security measure to prevent arbitrary chunk generation
        if (!world.hasChunkAt(entity.blockPosition()))
            return;
        if (type == 0) {
            SensingNearbyTeammatesEvent.execute(entity);
        }
    }
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MESSAGE_HANDLER.registerMessage(
            0,
            SensingNearbyTeammatesKeyEvent.class,
            SensingNearbyTeammatesKeyEvent::decode,
            SensingNearbyTeammatesKeyEvent::encode,
            SensingNearbyTeammatesKeyEvent::handler
        );
    }
}

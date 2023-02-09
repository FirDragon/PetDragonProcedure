package com.cute_dragon_project.xingxing.pet_dragon_procedure.events;

import com.cute_dragon_project.xingxing.pet_dragon_procedure.messages.MessageRegistry;
import com.cute_dragon_project.xingxing.pet_dragon_procedure.messages.SensingNearbyTeammatesMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class KeyClickEvent {
    public static final KeyMapping N_KEY = new KeyMapping("key.pet_dragon_procedure.n_key", GLFW.GLFW_KEY_APOSTROPHE, "key.categories.misc");

    @SubscribeEvent
    public static void registerKeyBindings(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(N_KEY);
    }

    @Mod.EventBusSubscriber({Dist.CLIENT})
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.KeyInputEvent event) {
            if (Minecraft.getInstance().screen == null) {
                if (event.getKey() == N_KEY.getKey().getValue()) {
                    if (event.getAction() == GLFW.GLFW_PRESS) {
                        MessageRegistry.MESSAGE_HANDLER.sendToServer(new SensingNearbyTeammatesMessage(0, 0));
                        //SensingNearbyTeammatesKeyEvent.pressAction(Minecraft.getInstance().player, 0, 0);
                    }
                }
            }
        }
    }
}
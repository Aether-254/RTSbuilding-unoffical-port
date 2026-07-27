package awa.Aether_254.rtsbuilding.client.bootstrap;

import awa.Aether_254.rtsbuilding.client.input.ClientInputHandler;
import awa.Aether_254.rtsbuilding.client.pathfinding.RtsMovementModeRegistry;
import awa.Aether_254.rtsbuilding.common.RtsEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;

public final class RtsClientBootstrap implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientKeyMappings.register();
        RtsMovementModeRegistry.init();
        RtsMovementModeRegistry.fireRegistrationEvent();
        EntityRendererRegistry.register(RtsEntities.RTS_CAMERA_ENTITY.get(), NoopRenderer::new);
        ClientTickEvents.START_CLIENT_TICK.register(client -> ClientInputHandler.onClientTickPre());
        ClientTickEvents.END_CLIENT_TICK.register(client -> ClientInputHandler.onClientTickPost());
    }
}

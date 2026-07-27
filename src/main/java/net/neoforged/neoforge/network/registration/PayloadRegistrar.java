package net.neoforged.neoforge.network.registration;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PayloadRegistrar {
    @FunctionalInterface
    public interface Handler<T extends CustomPacketPayload> {
        void handle(T payload, IPayloadContext context);
    }

    public <T extends CustomPacketPayload> void playToServer(
        CustomPacketPayload.Type<T> type,
        StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
        Handler<T> handler
    ) {
        PayloadTypeRegistry.playC2S().register(type, codec);
        ServerPlayNetworking.registerGlobalReceiver(type,
            (payload, context) -> handler.handle(payload, () -> context.player()));
    }

    public <T extends CustomPacketPayload> void playToClient(
        CustomPacketPayload.Type<T> type,
        StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
        Handler<T> handler
    ) {
        PayloadTypeRegistry.playS2C().register(type, codec);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientBridge.register(type, handler);
        }
    }

    private static final class ClientBridge {
        private static <T extends CustomPacketPayload> void register(
            CustomPacketPayload.Type<T> type, Handler<T> handler
        ) {
            ClientPlayNetworking.registerGlobalReceiver(type,
                (payload, context) -> handler.handle(payload, () -> context.player()));
        }
    }
}

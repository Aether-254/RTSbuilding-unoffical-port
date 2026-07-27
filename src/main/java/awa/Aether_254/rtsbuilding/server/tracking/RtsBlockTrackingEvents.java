package awa.Aether_254.rtsbuilding.server.tracking;

import awa.Aether_254.rtsbuilding.server.data.PlacedBlockTrackerData;
import awa.Aether_254.rtsbuilding.server.service.RtsProgressRefresher;
import awa.Aether_254.rtsbuilding.server.service.ServiceRegistry;
import awa.Aether_254.rtsbuilding.server.service.resolver.RtsLinkedStorageBlockEventHandler;
import awa.Aether_254.rtsbuilding.server.storage.session.RtsStorageSession;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;

public final class RtsBlockTrackingEvents {
    private RtsBlockTrackingEvents() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, level, hand, hit) -> {
            if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
                BlockPos clicked = hit.getBlockPos().immutable();
                BlockPos adjacent = clicked.relative(hit.getDirection()).immutable();
                serverLevel.getServer().execute(() -> {
                    trackPlaced(serverPlayer, serverLevel, clicked);
                    trackPlaced(serverPlayer, serverLevel, adjacent);
                });
            }
            return InteractionResult.PASS;
        });
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
                PlacedBlockTrackerData.get(serverLevel).clear(pos);
                RtsLinkedStorageBlockEventHandler.onLinkedStorageBlockBroken(serverLevel, pos);
                refresh(serverPlayer);
            }
        });
    }

    private static void trackPlaced(ServerPlayer player, ServerLevel level, BlockPos pos) {
        if (level.getBlockState(pos).isAir()) {
            return;
        }
        PlacedBlockTrackerData.get(level).mark(pos);
        RtsLinkedStorageBlockEventHandler.onLinkedStorageBlockPlaced(level, pos);
        refresh(player);
    }

    private static void refresh(ServerPlayer player) {
        RtsStorageSession session = ServiceRegistry.getInstance().session().getIfPresent(player);
        if (session != null) {
            RtsProgressRefresher.refreshWorkflowProgress(player, session);
        }
    }
}

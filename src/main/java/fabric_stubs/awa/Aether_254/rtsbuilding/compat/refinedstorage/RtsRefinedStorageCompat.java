package awa.Aether_254.rtsbuilding.compat.refinedstorage;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.items.IItemHandler;

public final class RtsRefinedStorageCompat {
    private RtsRefinedStorageCompat() {}
    public static boolean isAvailable() { return false; }
    public static boolean isNetworkNodePosition(ServerPlayer player, BlockPos pos) { return false; }
    public static IItemHandler createNetworkItemHandler(ServerPlayer player, BlockPos pos) { return null; }
}

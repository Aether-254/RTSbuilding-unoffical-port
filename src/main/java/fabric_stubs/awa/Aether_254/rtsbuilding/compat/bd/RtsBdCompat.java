package awa.Aether_254.rtsbuilding.compat.bd;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

public final class RtsBdCompat {
    public interface DirectExtractHandler {
        ItemStack tryExtractItem(Item target, int amount, boolean simulate);
    }

    private RtsBdCompat() {}

    public static boolean isAvailable() { return false; }
    public static boolean hasPrimaryNetwork(ServerPlayer player) { return false; }
    public static IItemHandler createNetworkItemHandler(ServerPlayer player) { return null; }
    public static IFluidHandler createNetworkFluidHandler(ServerPlayer player) { return null; }
    public static void releaseNetworkHandler(IItemHandler handler) {}
    public static void refreshNetworkHandler(IItemHandler handler) {}
    public static String getNetworkDisplayName(ServerPlayer player) { return "Beyond Dimensions Network"; }
}

package awa.Aether_254.rtsbuilding.compat.ae2;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public final class RtsAe2Compat {
    private RtsAe2Compat() {}

    public static boolean isAvailable() {
        return false;
    }

    public static IItemHandler createNetworkItemHandler(ServerPlayer player, BlockPos pos) {
        return null;
    }

    public static long getReportedCount(IItemHandler handler, int slot, ItemStack fallbackStack) {
        return fallbackStack == null ? 0 : fallbackStack.getCount();
    }

    public static void releaseNetworkHandler(IItemHandler handler) {}

    public static String resolveGuiBindingIconItemId(Level level, BlockPos pos, Direction face, String labelHint) {
        return "";
    }
}

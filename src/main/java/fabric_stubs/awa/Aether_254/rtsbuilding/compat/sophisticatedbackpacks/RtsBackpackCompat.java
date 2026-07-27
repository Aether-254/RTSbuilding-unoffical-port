package awa.Aether_254.rtsbuilding.compat.sophisticatedbackpacks;

import java.util.Optional;
import java.util.UUID;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;

public final class RtsBackpackCompat {
    private RtsBackpackCompat() {}
    public static boolean isAvailable() { return false; }
    public static boolean isBackpackBlockEntity(BlockEntity blockEntity) { return false; }
    public static Optional<UUID> getBackpackUuid(BlockEntity blockEntity) { return Optional.empty(); }
    public static Optional<String> getBackpackItemId(BlockEntity blockEntity) { return Optional.empty(); }
    public static Optional<IItemHandler> openBackpack(UUID uuid, String itemId) { return Optional.empty(); }
    public static Optional<IItemHandler> openBackpack(UUID uuid, String itemId, ServerPlayer player) {
        return Optional.empty();
    }
    public static Optional<IItemHandler> findBackpackHandlerByUuid(ServerPlayer player, UUID uuid) {
        return Optional.empty();
    }
    public static boolean isBackpackItem(ItemStack stack) { return false; }
}

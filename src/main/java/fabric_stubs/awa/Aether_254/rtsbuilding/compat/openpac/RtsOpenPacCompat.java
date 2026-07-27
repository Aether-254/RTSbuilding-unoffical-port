package awa.Aether_254.rtsbuilding.compat.openpac;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public final class RtsOpenPacCompat {
    private RtsOpenPacCompat() {}

    public static String progressionTeamKey(ServerPlayer player) { return player.getUUID().toString(); }
    public static String progressionTeamLabel(ServerPlayer player) { return player.getGameProfile().name(); }
    public static boolean canBreakBlock(ServerPlayer player, BlockPos pos, Direction face) { return true; }
    public static boolean canPlaceBlock(ServerPlayer player, BlockPos pos) { return true; }
    public static boolean canInteractBlock(ServerPlayer player, BlockPos pos, Direction face,
                                            InteractionHand hand, ItemStack heldItem) { return true; }
    public static boolean canInteractEntity(ServerPlayer player, Entity target, InteractionHand hand,
                                             ItemStack heldItem, boolean attack) { return true; }
}

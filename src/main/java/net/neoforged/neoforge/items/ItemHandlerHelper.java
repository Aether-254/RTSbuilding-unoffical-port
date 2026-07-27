package net.neoforged.neoforge.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class ItemHandlerHelper {
    private ItemHandlerHelper() {
    }

    public static ItemStack insertItemStacked(IItemHandler inventory, ItemStack stack, boolean simulate) {
        ItemStack remainder = stack;
        for (int slot = 0; slot < inventory.getSlots() && !remainder.isEmpty(); slot++) {
            if (!inventory.getStackInSlot(slot).isEmpty()) {
                remainder = inventory.insertItem(slot, remainder, simulate);
            }
        }
        for (int slot = 0; slot < inventory.getSlots() && !remainder.isEmpty(); slot++) {
            if (inventory.getStackInSlot(slot).isEmpty()) {
                remainder = inventory.insertItem(slot, remainder, simulate);
            }
        }
        return remainder;
    }

    public static void giveItemToPlayer(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}

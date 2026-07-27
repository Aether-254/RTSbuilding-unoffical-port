package awa.Aether_254.rtsbuilding.client.record;

import net.minecraft.world.item.ItemStack;

public record StorageEntry(ItemStack stack, String itemId, long count, String mod, String name) {
}

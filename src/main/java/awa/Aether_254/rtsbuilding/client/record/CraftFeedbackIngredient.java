package awa.Aether_254.rtsbuilding.client.record;

import net.minecraft.world.item.ItemStack;

public record CraftFeedbackIngredient(
        String itemId,
        String label,
        ItemStack preview,
        int count) {
}

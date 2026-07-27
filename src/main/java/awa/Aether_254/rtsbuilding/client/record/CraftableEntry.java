package awa.Aether_254.rtsbuilding.client.record;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public record CraftableEntry(
        ItemStack stack,
        String recipeId,
        String itemId,
        int resultCount,
        boolean craftable,
        String missingSummary,
        String mod,
        String name,
        List<CraftRecipeOption> recipeOptions) {
}

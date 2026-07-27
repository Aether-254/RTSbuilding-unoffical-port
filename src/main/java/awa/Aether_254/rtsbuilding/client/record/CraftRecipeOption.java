package awa.Aether_254.rtsbuilding.client.record;

public record CraftRecipeOption(
        String recipeId,
        int resultCount,
        boolean craftable,
        String summary,
        String missingSummary) {
}

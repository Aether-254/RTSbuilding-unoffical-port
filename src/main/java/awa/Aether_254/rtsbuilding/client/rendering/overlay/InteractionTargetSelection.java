package awa.Aether_254.rtsbuilding.client.rendering.overlay;

final class InteractionTargetSelection {
    private InteractionTargetSelection() {
    }

    static boolean shouldRenderEntityInsteadOfBlock(
            double entityDistSq, double blockDistSq, boolean entityWithinBounds) {
        return entityWithinBounds && entityDistSq <= blockDistSq;
    }
}

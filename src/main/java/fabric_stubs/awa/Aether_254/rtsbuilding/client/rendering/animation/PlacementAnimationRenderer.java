package awa.Aether_254.rtsbuilding.client.rendering.animation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class PlacementAnimationRenderer {
    private PlacementAnimationRenderer() {
    }

    public static void addPendingBatch(List<BlockPos> positions, BlockState state) {
        // World-render animation is intentionally optional on this Fabric port.
    }
}

package awa.Aether_254.rtsbuilding.client.rendering.culling;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class RtsCullingRenderInvalidator {
    private RtsCullingRenderInvalidator() {
    }

    public static void markBlocksDirty(BlockPos min, BlockPos max) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.levelRenderer == null || min == null || max == null) {
            return;
        }
        minecraft.levelRenderer.setBlocksDirty(
                Math.min(min.getX(), max.getX()) - 1,
                Math.min(min.getY(), max.getY()) - 1,
                Math.min(min.getZ(), max.getZ()) - 1,
                Math.max(min.getX(), max.getX()) + 1,
                Math.max(min.getY(), max.getY()) + 1,
                Math.max(min.getZ(), max.getZ()) + 1);
    }
}

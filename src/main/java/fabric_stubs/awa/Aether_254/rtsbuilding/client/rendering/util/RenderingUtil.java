package awa.Aether_254.rtsbuilding.client.rendering.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public final class RenderingUtil {
    private RenderingUtil() {
    }

    public static List<BlockPos> filterBlocksWithinBounds(
            List<BlockPos> blocks, double anchorX, double anchorZ, double maxRadius) {
        if (blocks == null || blocks.isEmpty()) {
            return blocks;
        }
        int minX = Mth.floor(anchorX - maxRadius);
        int maxX = Mth.ceil(anchorX + maxRadius) - 1;
        int minZ = Mth.floor(anchorZ - maxRadius);
        int maxZ = Mth.ceil(anchorZ + maxRadius) - 1;
        List<BlockPos> filtered = new ArrayList<>(blocks.size());
        for (BlockPos pos : blocks) {
            if (pos != null && pos.getX() >= minX && pos.getX() <= maxX
                    && pos.getZ() >= minZ && pos.getZ() <= maxZ) {
                filtered.add(pos);
            }
        }
        return filtered.isEmpty() ? List.of() : filtered;
    }
}

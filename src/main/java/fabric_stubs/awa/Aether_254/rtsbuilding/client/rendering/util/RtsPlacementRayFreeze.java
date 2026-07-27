package awa.Aether_254.rtsbuilding.client.rendering.util;

import net.minecraft.world.phys.Vec3;

public final class RtsPlacementRayFreeze {
    private static Vec3 origin;
    private static Vec3 direction;

    private RtsPlacementRayFreeze() {
    }

    public static void freeze(Vec3 rayOrigin, Vec3 rayDirection) {
        if (rayOrigin == null || rayDirection == null || rayDirection.lengthSqr() < 1.0E-8D) {
            clear();
            return;
        }
        origin = rayOrigin;
        direction = rayDirection.normalize();
    }

    public static boolean isFrozen() {
        return origin != null && direction != null;
    }

    public static Vec3 originOr(Vec3 fallback) {
        return origin == null ? fallback : origin;
    }

    public static Vec3 directionOr(Vec3 fallback) {
        return direction == null ? fallback : direction;
    }

    public static void clear() {
        origin = null;
        direction = null;
    }
}

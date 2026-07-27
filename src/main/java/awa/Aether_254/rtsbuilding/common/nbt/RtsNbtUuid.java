package awa.Aether_254.rtsbuilding.common.nbt;

import java.util.UUID;

import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;

public final class RtsNbtUuid {
    private RtsNbtUuid() {
    }

    public static void put(CompoundTag tag, String key, UUID value) {
        tag.putIntArray(key, UUIDUtil.uuidToIntArray(value));
    }

    public static UUID get(CompoundTag tag, String key) {
        return tag.getIntArray(key)
                .filter(values -> values.length == 4)
                .map(UUIDUtil::uuidFromIntArray)
                .orElseThrow(() -> new IllegalArgumentException("Missing UUID: " + key));
    }

    public static boolean has(CompoundTag tag, String key) {
        return tag.getIntArray(key).filter(values -> values.length == 4).isPresent();
    }
}

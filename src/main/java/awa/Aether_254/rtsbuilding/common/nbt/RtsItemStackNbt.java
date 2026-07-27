package awa.Aether_254.rtsbuilding.common.nbt;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;

public final class RtsItemStackNbt {
    private RtsItemStackNbt() {
    }

    public static CompoundTag save(ItemStack stack, HolderLookup.Provider registries) {
        return (CompoundTag) ItemStack.CODEC
                .encodeStart(registries.createSerializationContext(NbtOps.INSTANCE), stack)
                .getOrThrow();
    }

    public static ItemStack parse(HolderLookup.Provider registries, CompoundTag tag) {
        return ItemStack.CODEC
                .parse(registries.createSerializationContext(NbtOps.INSTANCE), tag)
                .result()
                .orElse(ItemStack.EMPTY);
    }
}

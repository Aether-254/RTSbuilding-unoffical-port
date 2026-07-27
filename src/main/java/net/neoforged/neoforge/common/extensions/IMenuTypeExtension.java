package net.neoforged.neoforge.common.extensions;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class IMenuTypeExtension {
    private IMenuTypeExtension() {}

    @FunctionalInterface
    public interface Factory<T extends AbstractContainerMenu> {
        T create(int syncId, Inventory inventory, RegistryFriendlyByteBuf data);
    }

    public static <T extends AbstractContainerMenu> MenuType<T> create(Factory<T> factory) {
        return new MenuType<>((syncId, inventory) -> factory.create(syncId, inventory,
            new RegistryFriendlyByteBuf(Unpooled.buffer(), RegistryAccess.EMPTY)), FeatureFlags.VANILLA_SET);
    }
}
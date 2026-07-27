package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.fluids.FluidType;

public final class NeoForgeRegistries {
    private NeoForgeRegistries() { }
    public static final class Keys {
        public static final ResourceKey<Registry<FluidType>> FLUID_TYPES = key("fluid_type");
        public static final ResourceKey<Registry<IngredientType<?>>> INGREDIENT_TYPES = key("ingredient_type");
        private static <T> ResourceKey<Registry<T>> key(String path) {
            return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("neoforge", path));
        }
    }
}

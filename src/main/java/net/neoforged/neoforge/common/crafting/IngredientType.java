package net.neoforged.neoforge.common.crafting;

import com.mojang.serialization.MapCodec;

public record IngredientType<T>(MapCodec<T> codec) {
}

package net.neoforged.neoforge.common.crafting;

import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;

public interface ICustomIngredient {
    Stream<ItemStack> getItems();
    boolean test(ItemStack stack);
    boolean isSimple();
    IngredientType<?> getType();
}

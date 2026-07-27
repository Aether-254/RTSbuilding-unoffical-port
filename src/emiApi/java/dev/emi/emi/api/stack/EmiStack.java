package dev.emi.emi.api.stack;

import net.minecraft.world.item.ItemStack;

public abstract class EmiStack implements EmiIngredient {
    public ItemStack getItemStack() {
        return ItemStack.EMPTY;
    }
}

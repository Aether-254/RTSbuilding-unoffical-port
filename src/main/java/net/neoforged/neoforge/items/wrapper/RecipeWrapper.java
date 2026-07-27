package net.neoforged.neoforge.items.wrapper;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.items.IItemHandler;

public final class RecipeWrapper implements RecipeInput {
	private final IItemHandler handler;

	public RecipeWrapper(IItemHandler handler) {
		this.handler = handler;
	}

	@Override
	public ItemStack getItem(int slot) {
		return handler.getStackInSlot(slot);
	}

	@Override
	public int size() {
		return handler.getSlots();
	}
}

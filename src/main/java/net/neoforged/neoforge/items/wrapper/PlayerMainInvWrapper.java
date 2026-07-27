package net.neoforged.neoforge.items.wrapper;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public final class PlayerMainInvWrapper implements IItemHandlerModifiable {
	private final Inventory inventory;

	public PlayerMainInvWrapper(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int getSlots() {
		return inventory.getNonEquipmentItems().size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory.getNonEquipmentItems().get(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		inventory.getNonEquipmentItems().set(slot, stack);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		ItemStack existing = getStackInSlot(slot);
		if (!existing.isEmpty() && !ItemStack.isSameItemSameComponents(existing, stack))
			return stack;
		int available = Math.min(getSlotLimit(slot), stack.getMaxStackSize()) - existing.getCount();
		if (available <= 0)
			return stack;
		int inserted = Math.min(available, stack.getCount());
		if (!simulate) {
			if (existing.isEmpty())
				setStackInSlot(slot, stack.copyWithCount(inserted));
			else
				existing.grow(inserted);
		}
		return inserted == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - inserted);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack existing = getStackInSlot(slot);
		int extracted = Math.min(amount, existing.getCount());
		if (extracted <= 0)
			return ItemStack.EMPTY;
		ItemStack result = existing.copyWithCount(extracted);
		if (!simulate) {
			existing.shrink(extracted);
			if (existing.isEmpty())
				setStackInSlot(slot, ItemStack.EMPTY);
		}
		return result;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}
}

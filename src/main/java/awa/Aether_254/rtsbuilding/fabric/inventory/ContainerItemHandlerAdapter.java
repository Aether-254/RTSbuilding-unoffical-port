package awa.Aether_254.rtsbuilding.fabric.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public final class ContainerItemHandlerAdapter implements IItemHandlerModifiable {
	private final Container container;
	private final int firstSlot;
	private final int slotCount;
	private final boolean respectValidation;

	public ContainerItemHandlerAdapter(Container container) {
		this(container, 0, container.getContainerSize(), true);
	}

	public ContainerItemHandlerAdapter(Container container, int firstSlot, int slotCount) {
		this(container, firstSlot, slotCount, true);
	}

	public ContainerItemHandlerAdapter(Container container, int firstSlot, int slotCount, boolean respectValidation) {
		this.container = container;
		this.firstSlot = firstSlot;
		this.slotCount = slotCount;
		this.respectValidation = respectValidation;
	}
	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		container.setItem(firstSlot + slot, stack);
		container.setChanged();
	}


	@Override
	public int getSlots() { return slotCount; }

	@Override
	public ItemStack getStackInSlot(int slot) { return container.getItem(firstSlot + slot); }

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (stack.isEmpty() || slot < 0 || slot >= slotCount || respectValidation && !container.canPlaceItem(firstSlot + slot, stack))
			return stack;
		ItemStack current = container.getItem(firstSlot + slot);
		if (!current.isEmpty() && !ItemStack.isSameItemSameComponents(current, stack))
			return stack;
		int room = Math.min(container.getMaxStackSize(stack), stack.getMaxStackSize()) - current.getCount();
		if (room <= 0)
			return stack;
		int inserted = Math.min(room, stack.getCount());
		if (!simulate) {
			container.setItem(firstSlot + slot, current.isEmpty() ? stack.copyWithCount(inserted) : current.copyWithCount(current.getCount() + inserted));
			container.setChanged();
		}
		return inserted == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - inserted);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (amount <= 0 || slot < 0 || slot >= slotCount)
			return ItemStack.EMPTY;
		ItemStack current = container.getItem(firstSlot + slot);
		if (current.isEmpty())
			return ItemStack.EMPTY;
		int extracted = Math.min(amount, current.getCount());
		ItemStack result = current.copyWithCount(extracted);
		if (!simulate) {
			container.setItem(firstSlot + slot, current.getCount() == extracted ? ItemStack.EMPTY : current.copyWithCount(current.getCount() - extracted));
			container.setChanged();
		}
		return result;
	}

	@Override
	public int getSlotLimit(int slot) { return container.getMaxStackSize(); }

	@Override
	public boolean isItemValid(int slot, ItemStack stack) { return !respectValidation || container.canPlaceItem(firstSlot + slot, stack); }
}
package net.neoforged.neoforge.items.wrapper;

import awa.Aether_254.rtsbuilding.fabric.inventory.ContainerItemHandlerAdapter;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

public final class CombinedInvWrapper implements IItemHandlerModifiable {
	private final IItemHandlerModifiable[] handlers;
	private final int slots;

	public CombinedInvWrapper(IItemHandlerModifiable... handlers) {
		this.handlers = handlers;
		int total = 0;
		for (IItemHandlerModifiable handler : handlers)
			total += handler.getSlots();
		slots = total;
	}

	public CombinedInvWrapper(Container... containers) {
		this(java.util.Arrays.stream(containers)
			.map(ContainerItemHandlerAdapter::new)
			.toArray(IItemHandlerModifiable[]::new));
	}

	@Override
	public int getSlots() {
		return slots;
	}

	private SlotRef slot(int slot) {
		if (slot < 0 || slot >= slots)
			throw new IndexOutOfBoundsException("Slot " + slot + " not in valid range [0," + slots + ")");
		for (IItemHandlerModifiable handler : handlers) {
			if (slot < handler.getSlots())
				return new SlotRef(handler, slot);
			slot -= handler.getSlots();
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		SlotRef ref = slot(slot);
		return ref.handler.getStackInSlot(ref.slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		SlotRef ref = slot(slot);
		return ref.handler.insertItem(ref.slot, stack, simulate);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		SlotRef ref = slot(slot);
		return ref.handler.extractItem(ref.slot, amount, simulate);
	}

	@Override
	public int getSlotLimit(int slot) {
		SlotRef ref = slot(slot);
		return ref.handler.getSlotLimit(ref.slot);
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		SlotRef ref = slot(slot);
		return ref.handler.isItemValid(ref.slot, stack);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		SlotRef ref = slot(slot);
		ref.handler.setStackInSlot(ref.slot, stack);
	}

	private record SlotRef(IItemHandlerModifiable handler, int slot) {}
}

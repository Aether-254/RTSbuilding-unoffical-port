package net.neoforged.neoforge.items;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {
	private final IItemHandler handler;
	private final int handlerSlot;

	public SlotItemHandler(IItemHandler handler, int slot, int x, int y) {
		super(handler instanceof Container container ? container : new SimpleContainer(handler.getSlots()), slot, x, y);
		this.handler = handler;
		this.handlerSlot = slot;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return handler.isItemValid(handlerSlot, stack);
	}

	@Override
	public ItemStack getItem() {
		return handler.getStackInSlot(handlerSlot);
	}

	@Override
	public void set(ItemStack stack) {
		if (handler instanceof IItemHandlerModifiable modifiable)
			modifiable.setStackInSlot(handlerSlot, stack);
	}

	@Override
	public ItemStack remove(int amount) {
		return handler.extractItem(handlerSlot, amount, false);
	}

	@Override
	public int getMaxStackSize() {
		return handler.getSlotLimit(handlerSlot);
	}
}

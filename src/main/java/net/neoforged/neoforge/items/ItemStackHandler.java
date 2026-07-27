package net.neoforged.neoforge.items;

import com.mojang.serialization.DynamicOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemStackHandler implements IItemHandlerModifiable, Container {
	protected NonNullList<ItemStack> stacks;

	public ItemStackHandler() {
		this(0);
	}

	public ItemStackHandler(int size) {
		stacks = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	@Override
	public int getSlots() {
		return stacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		validateSlotIndex(slot);
		return stacks.get(slot);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		validateSlotIndex(slot);
		stacks.set(slot, stack);
		onContentsChanged(slot);
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		validateSlotIndex(slot);
		if (stack.isEmpty() || !isItemValid(slot, stack))
			return stack;

		ItemStack existing = stacks.get(slot);
		int limit = Math.min(getSlotLimit(slot), stack.getMaxStackSize());
		if (!existing.isEmpty()) {
			if (!ItemStack.isSameItemSameComponents(existing, stack))
				return stack;
			limit -= existing.getCount();
		}
		if (limit <= 0)
			return stack;

		int inserted = Math.min(limit, stack.getCount());
		if (!simulate) {
			if (existing.isEmpty())
				stacks.set(slot, stack.copyWithCount(inserted));
			else
				existing.grow(inserted);
			onContentsChanged(slot);
		}
		return inserted == stack.getCount() ? ItemStack.EMPTY : stack.copyWithCount(stack.getCount() - inserted);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		validateSlotIndex(slot);
		if (amount <= 0)
			return ItemStack.EMPTY;
		ItemStack existing = stacks.get(slot);
		if (existing.isEmpty())
			return ItemStack.EMPTY;
		int extracted = Math.min(amount, existing.getCount());
		ItemStack result = existing.copyWithCount(extracted);
		if (!simulate) {
			existing.shrink(extracted);
			if (existing.isEmpty())
				stacks.set(slot, ItemStack.EMPTY);
			onContentsChanged(slot);
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

	protected void onContentsChanged(int slot) {
	}

	protected void validateSlotIndex(int slot) {
		if (slot < 0 || slot >= stacks.size())
			throw new RuntimeException("Slot " + slot + " not in valid range - [0," + stacks.size() + ")");
	}

	public CompoundTag serializeNBT(HolderLookup.Provider registries) {
		CompoundTag result = new CompoundTag();
		DynamicOps<net.minecraft.nbt.Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);
		net.minecraft.nbt.Tag encoded = ItemStack.OPTIONAL_CODEC.listOf().encodeStart(ops, List.copyOf(stacks))
			.result().orElseGet(ListTag::new);
		result.put("Items", encoded);
		return result;
	}

	public void deserializeNBT(HolderLookup.Provider registries, CompoundTag tag) {
		DynamicOps<net.minecraft.nbt.Tag> ops = registries.createSerializationContext(NbtOps.INSTANCE);
		List<ItemStack> decoded = ItemStack.OPTIONAL_CODEC.listOf().parse(ops, tag.get("Items"))
			.result().orElse(List.of());
		int size = Math.max(stacks.size(), decoded.size());
		stacks = NonNullList.withSize(size, ItemStack.EMPTY);
		for (int i = 0; i < decoded.size(); i++)
			stacks.set(i, decoded.get(i));
	}

	@Override
	public int getContainerSize() {
		return getSlots();
	}

	@Override
	public boolean isEmpty() {
		return stacks.stream().allMatch(ItemStack::isEmpty);
	}

	@Override
	public ItemStack getItem(int slot) {
		return getStackInSlot(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int amount) {
		return extractItem(slot, amount, false);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		ItemStack stack = getStackInSlot(slot);
		stacks.set(slot, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		setStackInSlot(slot, stack);
	}

	@Override
	public void setChanged() {
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		stacks.clear();
	}
}

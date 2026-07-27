package awa.Aether_254.rtsbuilding.server.storage.handler;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public final class RtsFabricCapabilities {
	private RtsFabricCapabilities() {
	}

	public static IItemHandler findItemHandler(Level level, BlockPos pos, Direction side) {
		Storage<ItemVariant> storage = ItemStorage.SIDED.find(level, pos, side);
		return storage == null ? null : new StorageItemHandler(storage);
	}

	public static IFluidHandler findFluidHandler(Level level, BlockPos pos, Direction side) {
		Storage<FluidVariant> storage = FluidStorage.SIDED.find(level, pos, side);
		return storage == null ? null : new StorageFluidHandler(storage);
	}

	public static IFluidHandlerItem findFluidHandler(ItemStack stack) {
		Storage<FluidVariant> storage = FluidStorage.ITEM.find(stack, ContainerItemContext.withConstant(stack));
		return storage == null ? null : new StorageFluidItemHandler(storage, stack);
	}

	private static final class StorageItemHandler implements IItemHandler {
		private final Storage<ItemVariant> storage;

		private StorageItemHandler(Storage<ItemVariant> storage) {
			this.storage = storage;
		}

		private List<StorageView<ItemVariant>> views() {
			List<StorageView<ItemVariant>> views = new ArrayList<>();
			storage.iterator().forEachRemaining(views::add);
			return views;
		}

		@Override
		public int getSlots() {
			return Math.max(1, views().size());
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			List<StorageView<ItemVariant>> views = views();
			if (slot < 0 || slot >= views.size())
				return ItemStack.EMPTY;
			StorageView<ItemVariant> view = views.get(slot);
			return view.isResourceBlank() ? ItemStack.EMPTY
				: view.getResource().toStack((int) Math.min(Integer.MAX_VALUE, view.getAmount()));
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			try (Transaction transaction = Transaction.openOuter()) {
				long inserted = storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
				if (!simulate)
					transaction.commit();
				return inserted >= stack.getCount() ? ItemStack.EMPTY
					: stack.copyWithCount(stack.getCount() - (int) inserted);
			}
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			ItemStack current = getStackInSlot(slot);
			if (current.isEmpty())
				return ItemStack.EMPTY;
			try (Transaction transaction = Transaction.openOuter()) {
				long extracted = storage.extract(ItemVariant.of(current), amount, transaction);
				if (!simulate)
					transaction.commit();
				return current.copyWithCount((int) extracted);
			}
		}

		@Override
		public int getSlotLimit(int slot) {
			List<StorageView<ItemVariant>> views = views();
			return slot >= 0 && slot < views.size() ? (int) Math.min(Integer.MAX_VALUE, views.get(slot).getCapacity()) : 64;
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return storage.supportsInsertion();
		}
	}

	private static class StorageFluidHandler implements IFluidHandler {
		private final Storage<FluidVariant> storage;

		private StorageFluidHandler(Storage<FluidVariant> storage) {
			this.storage = storage;
		}

		private List<StorageView<FluidVariant>> views() {
			List<StorageView<FluidVariant>> views = new ArrayList<>();
			storage.iterator().forEachRemaining(views::add);
			return views;
		}

		@Override
		public int getTanks() {
			return Math.max(1, views().size());
		}

		@Override
		public FluidStack getFluidInTank(int tank) {
			List<StorageView<FluidVariant>> views = views();
			if (tank < 0 || tank >= views.size() || views.get(tank).isResourceBlank())
				return FluidStack.EMPTY;
			StorageView<FluidVariant> view = views.get(tank);
			return new FluidStack(view.getResource().getFluid(), (int) Math.min(Integer.MAX_VALUE, view.getAmount()));
		}

		@Override
		public int getTankCapacity(int tank) {
			List<StorageView<FluidVariant>> views = views();
			return tank >= 0 && tank < views.size() ? (int) Math.min(Integer.MAX_VALUE, views.get(tank).getCapacity()) : 0;
		}

		@Override
		public boolean isFluidValid(int tank, FluidStack stack) {
			return storage.supportsInsertion();
		}

		@Override
		public int fill(FluidStack resource, FluidAction action) {
			try (Transaction transaction = Transaction.openOuter()) {
				long inserted = storage.insert(FluidVariant.of(resource.getFluid()), resource.getAmount(), transaction);
				if (action.execute())
					transaction.commit();
				return (int) inserted;
			}
		}

		@Override
		public FluidStack drain(FluidStack resource, FluidAction action) {
			try (Transaction transaction = Transaction.openOuter()) {
				long extracted = storage.extract(FluidVariant.of(resource.getFluid()), resource.getAmount(), transaction);
				if (action.execute())
					transaction.commit();
				return new FluidStack(resource.getFluid(), (int) extracted);
			}
		}

		@Override
		public FluidStack drain(int maxDrain, FluidAction action) {
			for (StorageView<FluidVariant> view : views()) {
				if (!view.isResourceBlank())
					return drain(new FluidStack(view.getResource().getFluid(), maxDrain), action);
			}
			return FluidStack.EMPTY;
		}
	}

	private static final class StorageFluidItemHandler extends StorageFluidHandler implements IFluidHandlerItem {
		private final ItemStack container;

		private StorageFluidItemHandler(Storage<FluidVariant> storage, ItemStack container) {
			super(storage);
			this.container = container;
		}

		@Override
		public ItemStack getContainer() {
			return container;
		}
	}
}

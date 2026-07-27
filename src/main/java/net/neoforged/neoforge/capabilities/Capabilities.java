package net.neoforged.neoforge.capabilities;

import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

public final class Capabilities {
	private Capabilities() {
	}

	public static final class ItemHandler {
		public static final BlockCapability<IItemHandler> BLOCK = new BlockCapability<>();
	}

	public static final class FluidHandler {
		public static final BlockCapability<IFluidHandler> BLOCK = new BlockCapability<>();
		public static final ItemCapability<IFluidHandler> ITEM = new ItemCapability<>();
	}

	public static final class BlockCapability<T> {
	}

	public static final class ItemCapability<T> {
	}
}

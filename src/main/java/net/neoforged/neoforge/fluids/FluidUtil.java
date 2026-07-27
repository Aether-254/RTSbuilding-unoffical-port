package net.neoforged.neoforge.fluids;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

import java.util.Optional;

public final class FluidUtil {
    private FluidUtil() {
    }

    public static ItemStack getFilledBucket(FluidStack stack) {
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(stack.getFluid().getBucket());
    }

    public static Optional<IFluidHandlerItem> getFluidHandler(ItemStack stack) {
        return Optional.empty();
    }
}

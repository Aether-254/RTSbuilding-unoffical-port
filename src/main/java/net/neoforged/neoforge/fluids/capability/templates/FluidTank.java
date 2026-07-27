package net.neoforged.neoforge.fluids.capability.templates;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FluidTank implements IFluidHandler {
    protected FluidStack fluid = FluidStack.EMPTY;
    protected final int capacity;

    public FluidTank(int capacity) { this.capacity = capacity; }
    public FluidStack getFluid() { return fluid; }
    public int getFluidAmount() { return fluid.getAmount(); }
    public int getCapacity() { return capacity; }
    public void setFluid(FluidStack stack) { fluid = stack == null ? FluidStack.EMPTY : stack; onContentsChanged(); }
    protected void onContentsChanged() { }
    public boolean isFluidValid(FluidStack stack) { return true; }
    @Override public int getTanks() { return 1; }
    @Override public FluidStack getFluidInTank(int tank) { return tank == 0 ? fluid : FluidStack.EMPTY; }
    @Override public int getTankCapacity(int tank) { return tank == 0 ? capacity : 0; }
    @Override public boolean isFluidValid(int tank, FluidStack stack) { return tank == 0 && isFluidValid(stack); }
    @Override public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource) || (!fluid.isEmpty() && !fluid.isFluidEqual(resource))) return 0;
        int filled = Math.min(capacity - getFluidAmount(), resource.getAmount());
        if (action.execute() && filled > 0) {
            if (fluid.isEmpty()) fluid = new FluidStack(resource.getFluid(), filled); else fluid.grow(filled);
            onContentsChanged();
        }
        return filled;
    }
    @Override public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || fluid.isEmpty() || !fluid.isFluidEqual(resource)) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }
    @Override public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = Math.min(maxDrain, getFluidAmount());
        if (drained <= 0) return FluidStack.EMPTY;
        FluidStack result = new FluidStack(fluid.getFluid(), drained);
        if (action.execute()) {
            fluid.shrink(drained);
            if (fluid.isEmpty()) fluid = FluidStack.EMPTY;
            onContentsChanged();
        }
        return result;
    }
    public CompoundTag writeToNBT(HolderLookup.Provider registries, CompoundTag tag) {
        tag.putString("Fluid", BuiltInRegistries.FLUID.getKey(fluid.getFluid()).toString());
        tag.putInt("Amount", fluid.getAmount());
        return tag;
    }
    public void readFromNBT(HolderLookup.Provider registries, CompoundTag tag) {
        Identifier id = Identifier.tryParse(tag.getStringOr("Fluid", "minecraft:empty"));
        setFluid(new FluidStack(id == null ? net.minecraft.world.level.material.Fluids.EMPTY
            : BuiltInRegistries.FLUID.getValue(id), tag.getIntOr("Amount", 0)));
    }
}

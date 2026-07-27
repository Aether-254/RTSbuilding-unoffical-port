package net.neoforged.neoforge.client.extensions.common;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IClientFluidTypeExtensions {
    Identifier getStillTexture();
    Identifier getFlowingTexture();
    default int getTintColor() { return 0xffffffff; }
    default int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) { return getTintColor(); }
    default int getTintColor(FluidStack stack) { return getTintColor(); }
}
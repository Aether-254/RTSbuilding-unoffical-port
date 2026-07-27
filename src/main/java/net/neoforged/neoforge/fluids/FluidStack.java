package net.neoforged.neoforge.fluids;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public final class FluidStack {
    public static final Codec<FluidStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BuiltInRegistries.FLUID.byNameCodec().fieldOf("id").forGetter(FluidStack::getFluid),
        Codec.INT.fieldOf("amount").forGetter(FluidStack::getAmount)
    ).apply(instance, FluidStack::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidStack> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(net.minecraft.core.registries.Registries.FLUID), FluidStack::getFluid,
        ByteBufCodecs.VAR_INT, FluidStack::getAmount,
        FluidStack::new
    );
    public static final FluidStack EMPTY = new FluidStack(Fluids.EMPTY, 0);

    public static FluidStack parseOptional(HolderLookup.Provider registries, Tag tag) {
        return CODEC.parse(registries.createSerializationContext(NbtOps.INSTANCE), tag)
            .result()
            .orElse(EMPTY);
    }

    private final Fluid fluid;
    private int amount;

    public FluidStack(Fluid fluid, int amount) {
        this.fluid = fluid == null ? Fluids.EMPTY : fluid;
        this.amount = Math.max(0, amount);
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = Math.max(0, amount);
    }

    public void shrink(int amount) {
        setAmount(this.amount - amount);
    }

    public void grow(int amount) {
        setAmount(this.amount + amount);
    }

    public boolean isFluidEqual(FluidStack other) {
        return other != null && fluid == other.fluid;
    }

    public boolean isEmpty() {
        return fluid == Fluids.EMPTY || amount <= 0;
    }

    public FluidStack copy() {
        return isEmpty() ? EMPTY : new FluidStack(fluid, amount);
    }
}

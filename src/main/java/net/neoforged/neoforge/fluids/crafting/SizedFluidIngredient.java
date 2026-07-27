package net.neoforged.neoforge.fluids.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public record SizedFluidIngredient(Fluid fluid, int amount) {
    public static final Codec<SizedFluidIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        BuiltInRegistries.FLUID.byNameCodec().fieldOf("fluid").forGetter(SizedFluidIngredient::fluid),
        Codec.INT.fieldOf("amount").forGetter(SizedFluidIngredient::amount)
    ).apply(instance, SizedFluidIngredient::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SizedFluidIngredient> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(net.minecraft.core.registries.Registries.FLUID), SizedFluidIngredient::fluid,
        ByteBufCodecs.VAR_INT, SizedFluidIngredient::amount,
        SizedFluidIngredient::new
    );

    public boolean test(FluidStack stack) {
        return !stack.isEmpty() && stack.getFluid() == fluid && stack.getAmount() >= amount;
    }
}

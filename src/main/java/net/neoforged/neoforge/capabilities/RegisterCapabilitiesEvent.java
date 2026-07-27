package net.neoforged.neoforge.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class RegisterCapabilitiesEvent {
	public <A, E extends BlockEntity> void registerBlockEntity(Capabilities.BlockCapability<A> capability,
		BlockEntityType<E> type, Provider<E, A> provider) {
	}

	@FunctionalInterface
	public interface Provider<E extends BlockEntity, A> {
		A get(E blockEntity, Direction side);
	}
}

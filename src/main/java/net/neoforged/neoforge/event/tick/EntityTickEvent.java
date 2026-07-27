package net.neoforged.neoforge.event.tick;

import net.minecraft.world.entity.Entity;

public final class EntityTickEvent {
	private EntityTickEvent() {}
	public record Post(Entity entity) {
		public Entity getEntity() { return entity; }
	}
}

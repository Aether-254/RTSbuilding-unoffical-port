package net.neoforged.neoforge.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public record EntityJoinLevelEvent(Entity entity, Level level) {
	public Entity getEntity() { return entity; }
	public Level getLevel() { return level; }
}

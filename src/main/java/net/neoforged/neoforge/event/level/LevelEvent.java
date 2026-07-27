package net.neoforged.neoforge.event.level;

import net.minecraft.world.level.Level;

public final class LevelEvent {
	private LevelEvent() {}
	public record Unload(Level level) {
		public Level getLevel() { return level; }
	}
}

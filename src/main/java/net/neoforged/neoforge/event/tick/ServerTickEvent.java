package net.neoforged.neoforge.event.tick;

import net.minecraft.server.MinecraftServer;

public final class ServerTickEvent {
	private ServerTickEvent() {}
	public record Post(MinecraftServer server) {
		public MinecraftServer getServer() { return server; }
	}
}

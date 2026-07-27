package net.neoforged.neoforge.client.event;

import net.minecraft.client.DeltaTracker;

public final class RenderFrameEvent {
	private RenderFrameEvent() {}
	public record Pre(DeltaTracker partialTick) {
		public DeltaTracker getPartialTick() { return partialTick; }
	}
}

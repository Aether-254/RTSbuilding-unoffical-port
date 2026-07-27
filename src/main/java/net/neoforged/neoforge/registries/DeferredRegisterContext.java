package net.neoforged.neoforge.registries;

import net.minecraft.resources.Identifier;

public final class DeferredRegisterContext {
	private static final ThreadLocal<Identifier> CURRENT_ID = new ThreadLocal<>();

	private DeferredRegisterContext() {
	}

	static void set(Identifier id) {
		CURRENT_ID.set(id);
	}

	static void clear() {
		CURRENT_ID.remove();
	}

	public static Identifier currentId() {
		return CURRENT_ID.get();
	}
}

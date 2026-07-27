package net.neoforged.fml;

import net.fabricmc.loader.api.FabricLoader;

public final class ModList {
    private static final ModList INSTANCE = new ModList();

    public static ModList get() {
        return INSTANCE;
    }

    public boolean isLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}

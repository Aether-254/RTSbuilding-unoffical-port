package net.neoforged.fml;

import net.neoforged.fml.config.ModConfig;

public final class ModContainer {
    public void registerConfig(ModConfig.Type type, Object spec) {
    }

    public void registerConfig(ModConfig.Type type, Object spec, String fileName) {
    }

    public <T> void registerExtensionPoint(Class<T> type, T extension) {
    }
}

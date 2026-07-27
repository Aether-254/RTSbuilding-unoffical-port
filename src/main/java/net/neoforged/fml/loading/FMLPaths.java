package net.neoforged.fml.loading;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class FMLPaths {
    public static final PathHolder CONFIGDIR = new PathHolder(FabricLoader.getInstance().getConfigDir());

    private FMLPaths() {
    }

    public record PathHolder(Path get) {
        public Path get() {
            return get;
        }
    }
}

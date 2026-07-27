package awa.Aether_254.rtsbuilding.client.screen.standalone;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RtsModConfigScreenContractTest {
    @Test
    void moduleConfigScreenUsesClothApiEntryPoint() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/awa/Aether_254/rtsbuilding/client/screen/standalone/RtsModConfigScreen.java"));

        assertTrue(source.contains("Config.createConfigScreen"));
        assertTrue(source.contains("ConfigBuilder"));
    }

    @Test
    void moduleConfigScreenExposesServerAreaMineLimits() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/awa/Aether_254/rtsbuilding/client/screen/standalone/RtsModConfigScreen.java"));

        assertTrue(source.contains("config.rtsbuilding.section.area_mining"));
        assertTrue(source.contains("config.rtsbuilding.area_mine_max_width"));
        assertTrue(source.contains("config.rtsbuilding.area_mine_max_height"));
        assertTrue(source.contains("config.rtsbuilding.area_mine_max_depth"));
        assertTrue(source.contains("config.rtsbuilding.area_mine_max_volume"));
        assertTrue(source.contains("config.rtsbuilding.area_destroy_max_targets"));
        assertTrue(source.contains("Config.saveAreaMineLimitSettings"));
    }

    @Test
    void configUsesClothApiAndPersistsToConfigDirectory() throws IOException {
        String config = Files.readString(Path.of("src/main/java/awa/Aether_254/rtsbuilding/Config.java"));

        assertTrue(config.contains("ConfigBuilder"));
        assertTrue(config.contains("FabricLoader.getInstance().getConfigDir"));
        assertTrue(config.contains("saveConfig"));
    }

    private static String slice(String source, String start, String end) {
        int startIndex = source.indexOf(start);
        int endIndex = source.indexOf(end, startIndex);
        assertTrue(startIndex >= 0, "Missing start marker: " + start);
        assertTrue(endIndex > startIndex, "Missing end marker after: " + start);
        return source.substring(startIndex, endIndex);
    }
}

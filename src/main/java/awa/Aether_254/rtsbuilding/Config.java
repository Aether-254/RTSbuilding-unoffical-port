package awa.Aether_254.rtsbuilding;

import awa.Aether_254.rtsbuilding.server.service.mining.RangeMiningHarvestTier;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.fluids.FluidType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Config {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("rtsbuilding.properties");
    private static final Properties CONFIG_PROPERTIES = new Properties();
    private static final List<ConfigValue<?>> VALUES = new ArrayList<>();

    public static final BooleanValue ENABLE_SURVIVAL_PROGRESSION = register(new BooleanValue("common.enableSurvivalProgression", false));
    public static final BooleanValue SHARE_SURVIVAL_PROGRESSION_WITH_TEAMS = register(new BooleanValue("common.shareSurvivalProgressionWithTeams", false));
    public static final IntValue MAX_ACTION_RADIUS_BLOCKS = register(new IntValue("common.maxActionRadiusBlocks", 128, 48, 512));
    public static final BooleanValue ENABLE_BLUEPRINTS = register(new BooleanValue("common.enableBlueprints", true));
    public static final IntValue MAX_BLUEPRINT_BLOCKS = register(new IntValue("common.maxBlueprintBlocks", 20000, 1, 200000));

    public static final BooleanValue USE_BLOCK_GHOST_PREVIEW = register(new BooleanValue("client.useBlockGhostPreview", true));
    public static final BooleanValue USE_PLACE_BLOCK_GHOST_ANIMATION = register(new BooleanValue("client.usePlaceBlockGhostAnimation", true));
    public static final BooleanValue USE_DESTROY_BLOCK_GHOST_ANIMATION = register(new BooleanValue("client.useDestroyBlockGhostAnimation", true));
    public static final BooleanValue USE_WIREFRAME_PREVIEW = register(new BooleanValue("client.useWireframePreview", false));
    public static final BooleanValue USE_PLACE_WIREFRAME_ANIMATION = register(new BooleanValue("client.usePlaceWireframeAnimation", false));
    public static final BooleanValue USE_DESTROY_WIREFRAME_ANIMATION = register(new BooleanValue("client.useDestroyWireframeAnimation", false));
    public static final BooleanValue USE_RANGE_DESTROY_SKELETON = register(new BooleanValue("client.useRangeDestroySkeleton", true));
    public static final BooleanValue REQUIRE_KEYBOARD_BATCH_CONFIRM = register(new BooleanValue("client.requireKeyboardBatchConfirm", true));
    public static final BooleanValue DEVELOPER_MODE = register(new BooleanValue("client.developerMode", false));

    public static final IntValue ULTIMINE_MAX_BLOCKS = register(new IntValue("mining.ultimineMaxBlocks", 256, 1, 4096));
    public static final IntValue AREA_MINE_MAX_SIZE = register(new IntValue("mining.areaMineMaxSize", 36, 1, 64));
    public static final IntValue AREA_MINE_MAX_VOLUME = register(new IntValue("mining.areaMineMaxVolume", 46656, 1, 262144));
    public static final IntValue AREA_MINE_MAX_WIDTH = register(new IntValue("mining.areaMineMaxWidth", 36, 1, 256));
    public static final IntValue AREA_MINE_MAX_HEIGHT = register(new IntValue("mining.areaMineMaxHeight", 36, 1, 256));
    public static final IntValue AREA_MINE_MAX_DEPTH = register(new IntValue("mining.areaMineMaxDepth", 36, 1, 256));
    public static final EnumValue<RangeMiningHarvestTier> AREA_MINE_MAX_HARVEST_TIER = register(new EnumValue<>("mining.areaMineMaxHarvestTier", RangeMiningHarvestTier.UNLIMITED, RangeMiningHarvestTier.class));
    public static final IntValue AE2_NETWORK_REFRESH_THROTTLE = register(new IntValue("storage.ae2NetworkRefreshThrottle", 10, 1, 200));
    public static final IntValue REFINED_STORAGE_NETWORK_REFRESH_THROTTLE = register(new IntValue("storage.refinedStorageNetworkRefreshThrottle", 10, 1, 200));
    public static final IntValue PAGE_CACHE_MAX_PLAYERS = register(new IntValue("storage.pageCacheMaxPlayers", 256, 1, 4096));
    public static final IntValue DEFAULT_STORAGE_PAGE_SIZE = register(new IntValue("storage.defaultStoragePageSize", 90, 1, 4096));
    public static final IntValue MAX_STORAGE_PAGE_SIZE = register(new IntValue("storage.maxStoragePageSize", 180, 1, 8192));
    public static final IntValue AREA_DESTROY_MAX_TARGETS = register(new IntValue("mining.areaDestroyMaxTargets", 98304, 1, 262144));
    public static final IntValue ULTIMINE_BLOCKS_PER_TICK = register(new IntValue("mining.ultimineBlocksPerTick", 8, 1, 128));
    public static final IntValue BUILD_BATCH_BLOCKS_PER_TICK = register(new IntValue("placement.buildBatchBlocksPerTick", 64, 1, 512));
    public static final IntValue BUILD_BATCH_MAX_QUEUED_JOBS = register(new IntValue("placement.buildBatchMaxQueuedJobs", 4, 1, 32));
    public static final IntValue TASK_ENGINE_MAX_UNITS_PER_TICK = register(new IntValue("taskEngine.maxUnitsPerTick", 256, 1, 4096));
    public static final IntValue TASK_ENGINE_MAX_UNITS_PER_SLICE = register(new IntValue("taskEngine.maxUnitsPerSlice", 32, 1, 512));
    public static final LongValue TASK_ENGINE_MAX_NANOS_PER_TICK = register(new LongValue("taskEngine.maxNanosPerTick", 4_000_000L, 250_000L, 20_000_000L));
    public static final DoubleValue REMOTE_POV_BLOCK_REACH = register(new DoubleValue("interaction.remotePovBlockReach", 4.0D, 1.0D, 16.0D));
    public static final DoubleValue DROP_SCAN_RADIUS = register(new DoubleValue("mining.dropScanRadius", 1.25D, 0.25D, 8.0D));
    public static final IntValue REMOTE_PLACE_SOUNDS_PER_TICK = register(new IntValue("placement.remoteBlockActionSoundsPerTick", 16, 0, 16));
    public static final IntValue INTERNAL_FLUID_CAPACITY_BUCKETS = register(new IntValue("fluid.internalFluidCapacityBuckets", 100, 1, 4096));

    static {
        loadConfig();
    }

    private static <T extends ConfigValue<?>> T register(T value) {
        VALUES.add(value);
        return value;
    }

    public static void setSurvivalProgressionEnabled(boolean enabled) {
        ENABLE_SURVIVAL_PROGRESSION.set(enabled);
        saveConfig();
    }

    public static int maxActionRadiusBlocks() {
        return MAX_ACTION_RADIUS_BLOCKS.intValue();
    }

    public static void setMaxActionRadiusBlocks(int radiusBlocks) {
        MAX_ACTION_RADIUS_BLOCKS.set(Math.max(48, Math.min(512, radiusBlocks)));
        saveConfig();
    }

    public static boolean areBlueprintsEnabled() {
        return ENABLE_BLUEPRINTS.getAsBoolean();
    }

    public static int maxBlueprintBlocks() {
        return MAX_BLUEPRINT_BLOCKS.intValue();
    }

    public static void saveGeneralSettings(boolean survivalEnabled, boolean shareWithTeams, int radiusBlocks,
            boolean blueprintsEnabled, int maxBlueprintBlocks) {
        ENABLE_SURVIVAL_PROGRESSION.set(survivalEnabled);
        SHARE_SURVIVAL_PROGRESSION_WITH_TEAMS.set(shareWithTeams);
        MAX_ACTION_RADIUS_BLOCKS.set(clampInt(radiusBlocks, 48, 512));
        ENABLE_BLUEPRINTS.set(blueprintsEnabled);
        MAX_BLUEPRINT_BLOCKS.set(clampInt(maxBlueprintBlocks, 1, 200000));
        saveConfig();
    }

    public static void saveAreaMineLimitSettings(int maxWidth, int maxHeight, int maxDepth,
            int maxVolume, int maxTargets, RangeMiningHarvestTier maxHarvestTier) {
        int width = clampInt(maxWidth, 1, 256);
        int height = clampInt(maxHeight, 1, 256);
        int depth = clampInt(maxDepth, 1, 256);
        AREA_MINE_MAX_WIDTH.set(width);
        AREA_MINE_MAX_HEIGHT.set(height);
        AREA_MINE_MAX_DEPTH.set(depth);
        AREA_MINE_MAX_VOLUME.set(clampInt(maxVolume, 1, 262144));
        AREA_DESTROY_MAX_TARGETS.set(clampInt(maxTargets, 1, 262144));
        AREA_MINE_MAX_HARVEST_TIER.set(maxHarvestTier == null ? RangeMiningHarvestTier.UNLIMITED : maxHarvestTier);
        AREA_MINE_MAX_SIZE.set(clampInt(Math.max(width, Math.max(height, depth)), 1, 64));
        saveConfig();
    }

    public static boolean isPlacementBlockGhostPreviewEnabled() {
        return USE_BLOCK_GHOST_PREVIEW.getAsBoolean();
    }

    public static void setPlacementBlockGhostPreviewEnabled(boolean enabled) {
        USE_BLOCK_GHOST_PREVIEW.set(enabled);
        saveConfig();
    }

    public static boolean isPlaceBlockGhostAnimationEnabled() {
        return USE_PLACE_BLOCK_GHOST_ANIMATION.getAsBoolean();
    }

    public static void setPlaceBlockGhostAnimationEnabled(boolean enabled) {
        USE_PLACE_BLOCK_GHOST_ANIMATION.set(enabled);
        saveConfig();
    }

    public static boolean isDestroyBlockGhostAnimationEnabled() {
        return USE_DESTROY_BLOCK_GHOST_ANIMATION.getAsBoolean();
    }

    public static void setDestroyBlockGhostAnimationEnabled(boolean enabled) {
        USE_DESTROY_BLOCK_GHOST_ANIMATION.set(enabled);
        saveConfig();
    }

    public static boolean isPlacementWireframePreviewEnabled() {
        return USE_WIREFRAME_PREVIEW.getAsBoolean();
    }

    public static void setPlacementWireframePreviewEnabled(boolean enabled) {
        USE_WIREFRAME_PREVIEW.set(enabled);
        saveConfig();
    }

    public static boolean isPlaceWireframeAnimationEnabled() {
        return USE_PLACE_WIREFRAME_ANIMATION.getAsBoolean();
    }

    public static void setPlaceWireframeAnimationEnabled(boolean enabled) {
        USE_PLACE_WIREFRAME_ANIMATION.set(enabled);
        saveConfig();
    }

    public static boolean isDestroyWireframeAnimationEnabled() {
        return USE_DESTROY_WIREFRAME_ANIMATION.getAsBoolean();
    }

    public static void setDestroyWireframeAnimationEnabled(boolean enabled) {
        USE_DESTROY_WIREFRAME_ANIMATION.set(enabled);
        saveConfig();
    }

    public static boolean isRangeDestroySkeletonEnabled() {
        return USE_RANGE_DESTROY_SKELETON.getAsBoolean();
    }

    public static void setRangeDestroySkeletonEnabled(boolean enabled) {
        USE_RANGE_DESTROY_SKELETON.set(enabled);
        saveConfig();
    }

    public static boolean isKeyboardBatchConfirmEnabled() {
        return REQUIRE_KEYBOARD_BATCH_CONFIRM.getAsBoolean();
    }

    public static void setKeyboardBatchConfirmEnabled(boolean enabled) {
        REQUIRE_KEYBOARD_BATCH_CONFIRM.set(enabled);
        saveConfig();
    }

    public static int ultimineMaxBlocks() {
        return ULTIMINE_MAX_BLOCKS.intValue();
    }

    public static int areaMineMaxSize() {
        return AREA_MINE_MAX_SIZE.intValue();
    }

    public static int areaMineMaxVolume() {
        return AREA_MINE_MAX_VOLUME.intValue();
    }

    public static int areaMineMaxWidth() {
        return AREA_MINE_MAX_WIDTH.intValue();
    }

    public static int areaMineMaxHeight() {
        return AREA_MINE_MAX_HEIGHT.intValue();
    }

    public static int areaMineMaxDepth() {
        return AREA_MINE_MAX_DEPTH.intValue();
    }

    public static RangeMiningHarvestTier areaMineMaxHarvestTier() {
        return AREA_MINE_MAX_HARVEST_TIER.get();
    }

    public static int ae2NetworkRefreshThrottle() {
        return AE2_NETWORK_REFRESH_THROTTLE.intValue();
    }

    public static int refinedStorageNetworkRefreshThrottle() {
        return REFINED_STORAGE_NETWORK_REFRESH_THROTTLE.intValue();
    }

    public static int pageCacheMaxPlayers() {
        return PAGE_CACHE_MAX_PLAYERS.intValue();
    }

    public static int defaultStoragePageSize() {
        return Math.min(DEFAULT_STORAGE_PAGE_SIZE.intValue(), maxStoragePageSize());
    }

    public static int maxStoragePageSize() {
        return MAX_STORAGE_PAGE_SIZE.intValue();
    }

    public static int areaDestroyMaxTargets() {
        return AREA_DESTROY_MAX_TARGETS.intValue();
    }

    public static int ultimineBlocksPerTick() {
        return ULTIMINE_BLOCKS_PER_TICK.intValue();
    }

    public static int buildBatchBlocksPerTick() {
        return BUILD_BATCH_BLOCKS_PER_TICK.intValue();
    }

    public static boolean isDeveloperModeEnabled() {
        return DEVELOPER_MODE.getAsBoolean();
    }

    public static void setDeveloperModeEnabled(boolean enabled) {
        DEVELOPER_MODE.set(enabled);
        saveConfig();
    }

    public static int buildBatchMaxQueuedJobs() {
        return BUILD_BATCH_MAX_QUEUED_JOBS.intValue();
    }

    public static int taskEngineMaxUnitsPerTick() {
        return TASK_ENGINE_MAX_UNITS_PER_TICK.intValue();
    }

    public static int taskEngineMaxUnitsPerSlice() {
        return TASK_ENGINE_MAX_UNITS_PER_SLICE.intValue();
    }

    public static long taskEngineMaxNanosPerTick() {
        return TASK_ENGINE_MAX_NANOS_PER_TICK.get();
    }

    public static double remotePovBlockReach() {
        return REMOTE_POV_BLOCK_REACH.doubleValue();
    }

    public static double dropScanRadius() {
        return DROP_SCAN_RADIUS.doubleValue();
    }

    public static int remotePlaceSoundsPerTick() {
        return REMOTE_PLACE_SOUNDS_PER_TICK.intValue();
    }

    public static long internalFluidCapacityMb() {
        return Math.max(1L, (long) INTERNAL_FLUID_CAPACITY_BUCKETS.intValue()) * FluidType.BUCKET_VOLUME;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.rtsbuilding.title"))
                .setSavingRunnable(Config::saveConfig);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory gameplay = builder.getOrCreateCategory(Component.translatable("config.rtsbuilding.section.gameplay"));
        gameplay.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.rtsbuilding.option.survival"), ENABLE_SURVIVAL_PROGRESSION.getAsBoolean())
                .setSaveConsumer(ENABLE_SURVIVAL_PROGRESSION::set)
                .build());
        gameplay.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.rtsbuilding.option.teams"), SHARE_SURVIVAL_PROGRESSION_WITH_TEAMS.getAsBoolean())
                .setSaveConsumer(SHARE_SURVIVAL_PROGRESSION_WITH_TEAMS::set)
                .build());
        gameplay.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.max_radius"), MAX_ACTION_RADIUS_BLOCKS.intValue())
                .setMin(48)
                .setMax(512)
                .setSaveConsumer(MAX_ACTION_RADIUS_BLOCKS::set)
                .build());

        ConfigCategory blueprints = builder.getOrCreateCategory(Component.translatable("config.rtsbuilding.section.blueprints"));
        blueprints.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.rtsbuilding.option.blueprints"), ENABLE_BLUEPRINTS.getAsBoolean())
                .setSaveConsumer(ENABLE_BLUEPRINTS::set)
                .build());
        blueprints.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.max_blueprint_blocks"), MAX_BLUEPRINT_BLOCKS.intValue())
                .setMin(1)
                .setMax(200000)
                .setSaveConsumer(MAX_BLUEPRINT_BLOCKS::set)
                .build());

        ConfigCategory mining = builder.getOrCreateCategory(Component.translatable("config.rtsbuilding.section.area_mining"));
        mining.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.area_mine_max_width"), AREA_MINE_MAX_WIDTH.intValue())
                .setMin(1)
                .setMax(256)
                .setSaveConsumer(AREA_MINE_MAX_WIDTH::set)
                .build());
        mining.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.area_mine_max_height"), AREA_MINE_MAX_HEIGHT.intValue())
                .setMin(1)
                .setMax(256)
                .setSaveConsumer(AREA_MINE_MAX_HEIGHT::set)
                .build());
        mining.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.area_mine_max_depth"), AREA_MINE_MAX_DEPTH.intValue())
                .setMin(1)
                .setMax(256)
                .setSaveConsumer(AREA_MINE_MAX_DEPTH::set)
                .build());
        mining.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.area_mine_max_volume"), AREA_MINE_MAX_VOLUME.intValue())
                .setMin(1)
                .setMax(262144)
                .setSaveConsumer(AREA_MINE_MAX_VOLUME::set)
                .build());
        mining.addEntry(entryBuilder.startIntField(Component.translatable("config.rtsbuilding.area_destroy_max_targets"), AREA_DESTROY_MAX_TARGETS.intValue())
                .setMin(1)
                .setMax(262144)
                .setSaveConsumer(AREA_DESTROY_MAX_TARGETS::set)
                .build());
        mining.addEntry(entryBuilder.startEnumSelector(Component.translatable("config.rtsbuilding.area_mine_max_harvest_tier"), RangeMiningHarvestTier.class, AREA_MINE_MAX_HARVEST_TIER.get())
                .setSaveConsumer(AREA_MINE_MAX_HARVEST_TIER::set)
                .build());

        ConfigCategory developer = builder.getOrCreateCategory(Component.translatable("config.rtsbuilding.section.developer"));
        developer.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.rtsbuilding.option.developer_mode"), DEVELOPER_MODE.getAsBoolean())
                .setSaveConsumer(DEVELOPER_MODE::set)
                .build());

        return builder.build();
    }

    private static void loadConfig() {
        if (!Files.exists(CONFIG_PATH)) {
            return;
        }
        try (InputStream stream = Files.newInputStream(CONFIG_PATH)) {
            CONFIG_PROPERTIES.load(stream);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load RTSBuilding config", exception);
        }
        for (ConfigValue<?> value : VALUES) {
            value.load(CONFIG_PROPERTIES);
        }
    }

    public static void saveConfig() {
        for (ConfigValue<?> value : VALUES) {
            value.save(CONFIG_PROPERTIES);
        }
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (OutputStream stream = Files.newOutputStream(CONFIG_PATH)) {
                CONFIG_PROPERTIES.store(stream, "RTSBuilding config");
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save RTSBuilding config", exception);
        }
    }

    private static int clampInt(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private abstract static class ConfigValue<T> {
        private final String key;
        private final T defaultValue;
        private T value;

        protected ConfigValue(String key, T defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.value = defaultValue;
        }

        public T get() {
            return this.value;
        }

        public void set(T value) {
            this.value = value;
        }

        protected void load(Properties properties) {
            String raw = properties.getProperty(this.key);
            if (raw == null) {
                this.value = this.defaultValue;
            } else {
                this.value = parse(raw);
            }
        }

        protected void save(Properties properties) {
            properties.setProperty(this.key, serialize(this.value));
        }

        protected abstract T parse(String raw);

        protected abstract String serialize(T value);
    }

    public static class BooleanValue extends ConfigValue<Boolean> {
        public BooleanValue(String key, boolean defaultValue) {
            super(key, defaultValue);
        }

        public boolean getAsBoolean() {
            return get();
        }

        @Override
        protected Boolean parse(String raw) {
            return Boolean.parseBoolean(raw);
        }

        @Override
        protected String serialize(Boolean value) {
            return Boolean.toString(value);
        }
    }

    public static class IntValue extends ConfigValue<Integer> {
        private final int min;
        private final int max;

        public IntValue(String key, int defaultValue, int min, int max) {
            super(key, defaultValue);
            this.min = min;
            this.max = max;
        }

        public int getAsInt() {
            return get();
        }

        public int intValue() {
            return get();
        }

        @Override
        public void set(Integer value) {
            super.set(Math.max(this.min, Math.min(this.max, value)));
        }

        @Override
        protected Integer parse(String raw) {
            return Integer.parseInt(raw);
        }

        @Override
        protected String serialize(Integer value) {
            return Integer.toString(value);
        }
    }

    public static class LongValue extends ConfigValue<Long> {
        private final long min;
        private final long max;

        public LongValue(String key, long defaultValue, long min, long max) {
            super(key, defaultValue);
            this.min = min;
            this.max = max;
        }

        public long getAsLong() {
            return get();
        }

        public long longValue() {
            return get();
        }

        @Override
        public void set(Long value) {
            super.set(Math.max(this.min, Math.min(this.max, value)));
        }

        @Override
        protected Long parse(String raw) {
            return Long.parseLong(raw);
        }

        @Override
        protected String serialize(Long value) {
            return Long.toString(value);
        }
    }

    public static class DoubleValue extends ConfigValue<Double> {
        private final double min;
        private final double max;

        public DoubleValue(String key, double defaultValue, double min, double max) {
            super(key, defaultValue);
            this.min = min;
            this.max = max;
        }

        public double getAsDouble() {
            return get();
        }

        public double doubleValue() {
            return get();
        }

        @Override
        public void set(Double value) {
            super.set(Math.max(this.min, Math.min(this.max, value)));
        }

        @Override
        protected Double parse(String raw) {
            return Double.parseDouble(raw);
        }

        @Override
        protected String serialize(Double value) {
            return Double.toString(value);
        }
    }

    public static class EnumValue<E extends Enum<E>> extends ConfigValue<E> {
        private final Class<E> enumClass;

        public EnumValue(String key, E defaultValue, Class<E> enumClass) {
            super(key, defaultValue);
            this.enumClass = enumClass;
        }

        @Override
        protected E parse(String raw) {
            return Enum.valueOf(this.enumClass, raw);
        }

        @Override
        protected String serialize(E value) {
            return value.name();
        }
    }
}


package awa.Aether_254.rtsbuilding.server.data;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/**
 * 追踪已放置方块位置的世界存档数据。
 * 用于记录玩家在建筑模式下放置的方块，以便在撤销或清除时恢复。
 */
public final class PlacedBlockTrackerData extends SavedData {
    /** 存档数据在 SavedData 中的注册名称 */
    private static final String DATA_NAME = "rtsbuilding_placed_blocks";
    /** NBT 键名：存储已放置方块位置的长整型数组 */
    private static final String KEY_PLACED = "placed";

    private static final Codec<PlacedBlockTrackerData> CODEC = Codec.LONG_STREAM
            .fieldOf(KEY_PLACED)
            .xmap(stream -> {
                PlacedBlockTrackerData data = new PlacedBlockTrackerData();
                stream.forEach(data.placedPositions::add);
                return data;
            }, data -> java.util.Arrays.stream(data.placedPositions.toLongArray()))
            .codec();
    private static final SavedDataType<PlacedBlockTrackerData> TYPE =
            new SavedDataType<>(DATA_NAME, PlacedBlockTrackerData::new, CODEC, DataFixTypes.LEVEL);

    /** 已放置方块位置集合（使用 long 编码的 BlockPos） */
    private final LongOpenHashSet placedPositions;

    /** 创建新的空追踪数据实例 */
    private PlacedBlockTrackerData() {
        this.placedPositions = new LongOpenHashSet();
    }

    /**
     * 获取指定世界的已放置方块追踪数据。
     * 如果不存在则创建新的实例。
     */
    public static PlacedBlockTrackerData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(TYPE);
    }

    /** 标记指定位置的方块为已放置状态 */
    public void mark(BlockPos pos) {
        if (this.placedPositions.add(pos.asLong())) {
            setDirty(); // 标记数据已变更，下次保存时写入磁盘
        }
    }

    /** 清除指定位置的已放置标记 */
    public void clear(BlockPos pos) {
        if (this.placedPositions.remove(pos.asLong())) {
            setDirty(); // 标记数据已变更，下次保存时写入磁盘
        }
    }

    /** 检查指定位置是否已被标记为已放置 */
    public boolean isPlaced(BlockPos pos) {
        return this.placedPositions.contains(pos.asLong());
    }
}


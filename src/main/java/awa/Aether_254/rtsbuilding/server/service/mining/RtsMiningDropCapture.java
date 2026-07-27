package awa.Aether_254.rtsbuilding.server.service.mining;

import awa.Aether_254.rtsbuilding.server.storage.session.RtsStorageSession;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 在原版已经计算完方块掉落、但掉落实体尚未进入世界时接管 RTS 挖掘掉落。
 *
 * <p>该类只负责界定一次同步破坏调用的所有权，并把事件中的精确 {@code ItemStack}
 * 交给轻量缓存；它不访问 AE/RS、不执行储存写入，也不改变非 RTS 挖掘。这样既保留
 * 其他模组对掉落列表的修改，又消除“生成实体后再按半径扫描”造成的移动、拾取竞争窗口。</p>
 */
public final class RtsMiningDropCapture {
    private RtsMiningDropCapture() {
    }

    /** 在一次同步方块破坏期间开启精确掉落接管；嵌套调用按栈恢复上一层上下文。 */
    public static <T> T capture(
            ServerPlayer player, RtsStorageSession session, BlockPos pos, Supplier<T> destruction) {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(session, "session");
        Objects.requireNonNull(destruction, "destruction");
        T result = destruction.get();
        if (RtsMiningValidator.canAutoStoreDrops(player, session)) {
            RtsDropAbsorber.absorbNearbyMinedDrops(player, pos, session);
        }
        return result;
    }
}

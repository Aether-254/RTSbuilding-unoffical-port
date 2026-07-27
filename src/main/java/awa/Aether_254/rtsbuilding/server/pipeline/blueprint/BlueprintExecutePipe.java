package awa.Aether_254.rtsbuilding.server.pipeline.blueprint;

import awa.Aether_254.rtsbuilding.Config;
import awa.Aether_254.rtsbuilding.common.blueprint.model.RtsBlueprint;
import awa.Aether_254.rtsbuilding.common.blueprint.transform.BlueprintTransform;
import awa.Aether_254.rtsbuilding.network.blueprint.BlueprintNetworkHandlers;
import awa.Aether_254.rtsbuilding.network.blueprint.S2CBlueprintStatusPayload;
import awa.Aether_254.rtsbuilding.server.pipeline.context.BlueprintContext;
import awa.Aether_254.rtsbuilding.server.pipeline.core.PipelineContext;
import awa.Aether_254.rtsbuilding.server.pipeline.core.PipelinePipe;
import awa.Aether_254.rtsbuilding.server.pipeline.core.PipelineResult;
import awa.Aether_254.rtsbuilding.server.task.RtsTaskEngine;
import net.minecraft.core.BlockPos;

/**
 * 蓝图命令的同步准入阶段。
 *
 * <p>这里只执行固定成本校验和旋转中心计算。随蓝图大小增长的计划、排序、材料检查与
 * 世界写入全部由统一 Task Engine 在数量和纳秒预算内推进。</p>
 */
public final class BlueprintExecutePipe implements PipelinePipe<PipelineContext> {
    @Override
    public PipelineResult execute(PipelineContext ctx) {
        BlueprintContext bctx = BlueprintContext.require(ctx);
        if (!Config.areBlueprintsEnabled()) {
            send(bctx, S2CBlueprintStatusPayload.ERROR,
                    "screen.rtsbuilding.blueprints.status.disabled", "");
            return PipelineResult.failure("Blueprints disabled by config");
        }

        RtsBlueprint blueprint = bctx.getBlueprint();
        if (blueprint == null || blueprint.blocks().isEmpty()) {
            send(bctx, S2CBlueprintStatusPayload.ERROR,
                    "screen.rtsbuilding.blueprints.status.empty", "");
            return PipelineResult.failure("Blueprint is empty");
        }
        int maxBlocks = Config.maxBlueprintBlocks();
        if (blueprint.blockCount() > maxBlocks) {
            send(bctx, S2CBlueprintStatusPayload.ERROR,
                    "screen.rtsbuilding.blueprints.status.too_many_blocks",
                    blueprint.blockCount() + "/" + maxBlocks);
            return PipelineResult.failure("Blueprint exceeds max block count " + maxBlocks);
        }

        BlockPos anchor = bctx.getAnchor();
        if (anchor == null) {
            send(bctx, S2CBlueprintStatusPayload.ERROR,
                    "screen.rtsbuilding.blueprints.status.parse_failed", "");
            return PipelineResult.failure("Invalid anchor position");
        }

        BlockPos centerOffset = BlueprintTransform.centerRotationOffset(
                blueprint.size(), bctx.getYRotationSteps(),
                bctx.getXRotationSteps(), bctx.getZRotationSteps());
        bctx.setData(BlueprintContext.KEY_CENTER_OFFSET, centerOffset);
        bctx.setData(BlueprintContext.KEY_SOURCE_DIMENSION, ctx.player().level().dimension());
        bctx.setPlacedCount(0);
        bctx.setSkippedMissing(0);
        bctx.setSkippedUnsupported(0);
        bctx.setSkippedMissingBlocks(0);
        bctx.setSkippedBlocked(0);
        bctx.setPreparing(true);

        var outcome = RtsTaskEngine.INSTANCE.queueDurableBlueprint(bctx);
        if (outcome == awa.Aether_254.rtsbuilding.server.task.DurableBlueprintTaskBridge.QueueResult.QUEUE_FULL
                || outcome == awa.Aether_254.rtsbuilding.server.task.DurableBlueprintTaskBridge.QueueResult.MEMORY_BUDGET_FULL) {
            send(bctx, S2CBlueprintStatusPayload.ERROR,
                    "screen.rtsbuilding.blueprints.status.admission_busy", "");
            return PipelineResult.failure("Durable blueprint admission queue full");
        }
        send(bctx, S2CBlueprintStatusPayload.INFO,
                "screen.rtsbuilding.blueprints.status.queued",
                Integer.toString(blueprint.blockCount()));
        return PipelineResult.success();
    }

    private static void send(BlueprintContext context, byte status, String key, String detail) {
        BlueprintNetworkHandlers.send(context.player(), status, key, detail);
    }
}

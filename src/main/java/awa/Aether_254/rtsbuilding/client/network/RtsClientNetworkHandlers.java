package awa.Aether_254.rtsbuilding.client.network;


import awa.Aether_254.rtsbuilding.client.controller.ClientRtsController;
import awa.Aether_254.rtsbuilding.client.developer.RtsDeveloperScenarioTracker;
import awa.Aether_254.rtsbuilding.client.screen.blueprint.BlueprintPanel;
import awa.Aether_254.rtsbuilding.client.screen.culling.RtsCullingClientState;
import awa.Aether_254.rtsbuilding.client.screen.handler.PlacementHistoryManager;
import awa.Aether_254.rtsbuilding.client.screen.standalone.BuilderScreen;
import awa.Aether_254.rtsbuilding.client.screen.workflow.RtsBlueprintResumePanel;
import awa.Aether_254.rtsbuilding.client.screen.workflow.RtsResumePlacementPanel;
import awa.Aether_254.rtsbuilding.client.sound.RtsBlockActionSoundPlayer;
import awa.Aether_254.rtsbuilding.network.blueprint.S2CBlueprintStatusPayload;
import awa.Aether_254.rtsbuilding.network.builder.*;
import awa.Aether_254.rtsbuilding.network.camera.S2CRtsCameraAnchorPayload;
import awa.Aether_254.rtsbuilding.network.camera.S2CRtsCameraStatePayload;
import awa.Aether_254.rtsbuilding.network.craft.S2CRtsCraftFeedbackPayload;
import awa.Aether_254.rtsbuilding.network.craft.S2CRtsCraftablesPayload;
import awa.Aether_254.rtsbuilding.network.culling.S2CRtsCullingStatePayload;
import awa.Aether_254.rtsbuilding.network.feedback.S2CRtsDamageFeedbackPayload;
import awa.Aether_254.rtsbuilding.network.plugin.S2CRtsPluginStatePayload;
import awa.Aether_254.rtsbuilding.network.progression.S2CRtsProgressionStatePayload;
import awa.Aether_254.rtsbuilding.network.progression.S2CRtsQuestDetectStatusPayload;
import awa.Aether_254.rtsbuilding.network.storage.S2CRtsRemoteMenuHintPayload;
import awa.Aether_254.rtsbuilding.network.storage.S2CRtsStorageDirtyPayload;
import awa.Aether_254.rtsbuilding.network.storage.S2CRtsStoragePagePayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class RtsClientNetworkHandlers {
    private RtsClientNetworkHandlers() {
    }

    public static void handleCameraState(S2CRtsCameraStatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyServerCameraState(payload));
    }

    public static void handleCameraAnchor(S2CRtsCameraAnchorPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyServerCameraAnchor(payload));
    }

    public static void handleStoragePage(S2CRtsStoragePagePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientRtsController.get().applyStoragePage(payload);
            RtsDeveloperScenarioTracker.getInstance().record("storage_page_received", "page=" + payload.page());
        });
    }

    public static void handleStorageDirty(S2CRtsStorageDirtyPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyStorageDirty(payload));
    }

    public static void handleRemoteMenuHint(S2CRtsRemoteMenuHintPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyRemoteMenuHint(payload));
    }

    public static void handleCraftables(S2CRtsCraftablesPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyCraftables(payload));
    }

    public static void handleCraftFeedback(S2CRtsCraftFeedbackPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyCraftFeedback(payload));
    }

    public static void handleCullingState(S2CRtsCullingStatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> RtsCullingClientState.applyCurrentWorldState(payload));
    }

    public static void handleDamageFeedback(S2CRtsDamageFeedbackPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyDamageFeedback(payload));
    }

    public static void handleQuestDetectStatus(S2CRtsQuestDetectStatusPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyQuestDetectStatus(payload));
    }

    public static void handleMineProgress(S2CRtsMineProgressPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyMineProgress(payload));
    }

    public static void handleUltimineProgress(S2CRtsUltimineProgressPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyUltimineProgress(payload));
    }

    public static void handleHarvestTierSkipped(
            S2CRtsHarvestTierSkippedPayload payload,
            IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof BuilderScreen builderScreen) {
                builderScreen.getShapeController()
                        .removeConfirmedRangeDestroyPreviewBlocks(payload.positions());
            }
        });
    }

    public static void handlePlaceAnimation(S2CRtsPlaceAnimationPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            RtsDeveloperScenarioTracker.getInstance().record(
                    "place_confirmed", "pos=" + payload.pos().toShortString());
        });
    }

    public static void handleBreakAnimation(S2CRtsBreakAnimationPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            RtsDeveloperScenarioTracker.getInstance().record(
                    "break_confirmed", "pos=" + payload.pos().toShortString());
        });
    }

    public static void handleBlockActionSound(S2CRtsBlockActionSoundPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> RtsBlockActionSoundPlayer.play(payload));
    }

    public static void handleProgressionState(S2CRtsProgressionStatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyProgressionState(payload));
    }

    public static void handlePluginState(S2CRtsPluginStatePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientRtsController.get().applyPluginState(payload));
    }

    public static void handleHistorySync(S2CRtsHistorySyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> PlacementHistoryManager.syncHistoryState(payload.undoSize()));
    }

    public static void handleWorkflowProgress(S2CRtsWorkflowProgressPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientRtsController.get().applyWorkflowProgress(payload);
            RtsDeveloperScenarioTracker.getInstance().record(
                    "workflow_update_received", "completed=" + payload.completedBlocks()
                            + ";total=" + payload.totalBlocks() + ";failed=" + payload.failedBlocks());
        });
    }

    public static void handleWorkflowProgressBatch(S2CRtsWorkflowProgressBatchPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientRtsController.get().applyWorkflowProgressBatch(payload);
            RtsDeveloperScenarioTracker.getInstance().record(
                    "workflow_update_received", "entries=" + payload.entries().size());
        });
    }

    public static void handleResumePlacementScan(S2CRtsResumePlacementScanPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientRtsController controller = ClientRtsController.get();
            controller.applyResumePlacementScan(payload);
            // 打开重启面板
            if (Minecraft.getInstance().screen instanceof BuilderScreen bs) {
                RtsResumePlacementPanel panel = bs.getResumePlacementPanel();
                panel.openWithData(payload);
            }
        });
    }

    public static void handleBlueprintResumeScan(S2CRtsBlueprintResumeScanPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().screen instanceof BuilderScreen bs) {
                RtsBlueprintResumePanel panel = bs.getBlueprintResumePanel();
                panel.openWithData(payload);
            }
        });
    }

    public static void handleBlueprintStatus(S2CBlueprintStatusPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> BlueprintPanel.setStatus(payload.status(), payload.messageKey(), payload.detail()));
    }
}

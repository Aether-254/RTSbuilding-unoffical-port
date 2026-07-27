package awa.Aether_254.rtsbuilding.client.util;


import awa.Aether_254.rtsbuilding.client.controller.ClientRtsController;
import awa.Aether_254.rtsbuilding.client.popup.RtsCraftQuantityDialog;
import awa.Aether_254.rtsbuilding.client.record.CraftableEntry;
/**
 * UI-only helper for the extracted craftables panel. It prepares dialog state
 * and forwards confirmed requests back to {@link ClientRtsController}, but does
 * not own recipe execution or server synchronization.
 */
public final class RtsCraftablesUiHelper {
    private RtsCraftablesUiHelper() {
    }

    public static void openCraftQuantityDialog(RtsCraftQuantityDialog dialog, CraftableEntry entry) {
        if (dialog == null || entry == null || !entry.craftable()) {
            return;
        }
        dialog.open(
                entry.stack().getHoverName().getString(),
                entry.stack(),
                entry.recipeOptions(),
                1);
    }

    public static void submitPendingCraftRequest(RtsCraftQuantityDialog dialog, ClientRtsController controller) {
        if (dialog == null || controller == null) {
            return;
        }
        RtsCraftQuantityDialog.Request request = dialog.consumePendingRequest();
        if (request == null) {
            return;
        }
        controller.craftRecipeToLinked(request.recipeId(), request.craftCount());
    }

    public static String normalizeSearchDraft(String value) {
        return value == null ? "" : value.trim();
    }
}

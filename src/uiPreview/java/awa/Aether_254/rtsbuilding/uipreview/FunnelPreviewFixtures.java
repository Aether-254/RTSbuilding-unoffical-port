package awa.Aether_254.rtsbuilding.uipreview;

import awa.Aether_254.rtsbuilding.uicore.funnel.FunnelUiEntry;
import awa.Aether_254.rtsbuilding.uicore.funnel.FunnelUiState;

import java.util.ArrayList;
import java.util.List;

/** 漏斗缓存 preview-only 有界快照。 */
final class FunnelPreviewFixtures {
    private FunnelPreviewFixtures() {
    }

    static boolean supports(UiPreviewScenario.Variant variant) {
        return variant == UiPreviewScenario.Variant.FUNNEL_EMPTY
                || variant == UiPreviewScenario.Variant.FUNNEL_ROWS;
    }

    static FunnelUiState forScenario(UiPreviewScenario scenario, UiMainlineAssets assets,
                                     int visibleCapacity) {
        if (scenario.variant() == UiPreviewScenario.Variant.FUNNEL_EMPTY) {
            return new FunnelUiState(true, true, 0, visibleCapacity, -1,
                    new ArrayList<FunnelUiEntry>());
        }
        List<FunnelUiEntry> rows = new ArrayList<FunnelUiEntry>();
        List<String> names = assets.itemNames();
        int count = Math.min(visibleCapacity, 8);
        for (int index = 0; index < count; index++) {
            String name = names.get(index % names.size());
            rows.add(new FunnelUiEntry(index, "rtsbuilding:" + name,
                    "Buffer " + (index + 1), (index + 1L) * 128L));
        }
        return new FunnelUiState(true, true, 2000, visibleCapacity, 1, rows);
    }
}

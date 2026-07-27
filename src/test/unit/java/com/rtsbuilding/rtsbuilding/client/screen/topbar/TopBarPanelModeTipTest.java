package awa.Aether_254.rtsbuilding.client.screen.topbar;

import awa.Aether_254.rtsbuilding.common.build.BuilderMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TopBarPanelModeTipTest {
    @Test
    void funnelModeExposesItsContextualTip() {
        assertEquals("screen.rtsbuilding.mode_tip.funnel",
                TopBarPanel.modeTipKey(BuilderMode.FUNNEL));
    }

    @Test
    void unrelatedModesDoNotShowTheFunnelTip() {
        assertEquals("", TopBarPanel.modeTipKey(BuilderMode.INTERACT));
        assertEquals("", TopBarPanel.modeTipKey(BuilderMode.LINK_STORAGE));
        assertEquals("", TopBarPanel.modeTipKey(BuilderMode.ROTATE));
    }
}

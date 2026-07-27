package awa.Aether_254.rtsbuilding.server.performance;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageDirtyRefreshContractTest {
    private static String source(String relative) throws IOException {
        return Files.readString(Path.of(System.getProperty("user.dir"), "src", "main", "java")
                .resolve(relative));
    }

    @Test
    void clientUsesEffectiveStorageTabInsteadOfTreatingBuilderScreenAsVisible() throws IOException {
        String controller = source("awa/Aether_254/rtsbuilding/client/controller/ClientRtsController.java");
        String screen = source("awa/Aether_254/rtsbuilding/client/screen/standalone/BuilderScreen.java");
        String bottomPanel = source("awa/Aether_254/rtsbuilding/client/screen/panel/BottomPanel.java");

        assertTrue(controller.contains("builderScreen.isStorageViewVisible()"));
        assertTrue(screen.contains("this.bottomPanel.isStorageBrowserVisible()"));
        assertTrue(bottomPanel.contains("activeBottomPanelTab() == BottomPanelLayoutTypes.BottomPanelTab.STORAGE"),
                "BuilderScreen 虽已打开，创造/蓝图标签仍必须保持 0 次自动构页");
        assertFalse(controller.contains("tickStorageAutoRefresh(this.storageStateManager.isStorageViewDirty())"));
    }

    @Test
    void pageServiceQueuesEveryCallerInsteadOfBuildingImmediately() throws IOException {
        String pageService = source("awa/Aether_254/rtsbuilding/server/service/impl/RtsPageServiceImpl.java");
        assertTrue(pageService.contains("RtsStoragePageRequestCoalescer.enqueue"));
        assertTrue(pageService.contains("private void buildPageNow"));
        assertFalse(pageService.contains("public void buildPageNow"));
    }
}

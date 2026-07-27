package awa.Aether_254.rtsbuilding.compat.emi;

import awa.Aether_254.rtsbuilding.client.screen.standalone.BuilderScreen;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

public final class RtsEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addDragDropHandler(BuilderScreen.class, new RtsEmiDragDropHandler());
    }
}

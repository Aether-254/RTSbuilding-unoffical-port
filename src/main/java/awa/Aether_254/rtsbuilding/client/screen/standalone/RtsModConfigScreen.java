package awa.Aether_254.rtsbuilding.client.screen.standalone;

import awa.Aether_254.rtsbuilding.Config;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class RtsModConfigScreen extends Screen {
    private final Screen parent;

    public RtsModConfigScreen(Screen parent) {
        super(Component.translatable("config.rtsbuilding.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.minecraft.setScreen(Config.createConfigScreen(this.parent));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

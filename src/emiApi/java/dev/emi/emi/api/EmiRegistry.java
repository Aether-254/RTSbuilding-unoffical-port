package dev.emi.emi.api;

import net.minecraft.client.gui.screens.Screen;

public interface EmiRegistry {
    <T extends Screen> void addDragDropHandler(Class<T> screenClass, EmiDragDropHandler<T> handler);
}

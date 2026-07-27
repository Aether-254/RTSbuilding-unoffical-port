package dev.emi.emi.api;

import dev.emi.emi.api.stack.EmiIngredient;
import net.minecraft.client.gui.screens.Screen;

public interface EmiDragDropHandler<T extends Screen> {
    boolean dropStack(T screen, EmiIngredient stack, int x, int y);
}

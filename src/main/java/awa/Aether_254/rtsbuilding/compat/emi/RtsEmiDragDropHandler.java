package awa.Aether_254.rtsbuilding.compat.emi;

import awa.Aether_254.rtsbuilding.client.screen.standalone.BuilderScreen;
import awa.Aether_254.rtsbuilding.common.build.BuilderMode;
import dev.emi.emi.api.EmiDragDropHandler;
import dev.emi.emi.api.stack.EmiIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class RtsEmiDragDropHandler implements EmiDragDropHandler<BuilderScreen> {
    @Override
    public boolean dropStack(BuilderScreen screen, EmiIngredient ingredient, int x, int y) {
        if (ingredient == null || ingredient.getEmiStacks().isEmpty()) {
            return false;
        }
        ItemStack stack = ingredient.getEmiStacks().getFirst().getItemStack();
        if (stack.isEmpty()) {
            return false;
        }
        Identifier id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        screen.uiController().selectItemForPlacement(
                id.toString(), stack.getHoverName().getString(), stack.copy());
        screen.uiController().setMode(BuilderMode.INTERACT);
        return true;
    }
}

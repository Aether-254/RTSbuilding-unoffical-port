package awa.Aether_254.rtsbuilding.client.rendering.builder;

import awa.Aether_254.rtsbuilding.client.controller.ClientRtsController;
import awa.Aether_254.rtsbuilding.common.placement.PlacementStatePreset;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class BuildGhostBlockStateResolver {
    private BuildGhostBlockStateResolver() {
    }

    public static BlockState resolve(Minecraft minecraft, BlockPos targetPos) {
        ClientRtsController controller = ClientRtsController.get();
        ItemStack stack = controller.getSelectedItemPreview();
        if (stack.isEmpty() && minecraft.player != null) {
            stack = minecraft.player.getMainHandItem();
        }
        if (!(stack.getItem() instanceof BlockItem blockItem)) {
            return null;
        }
        BlockState state = resolveStateWithCamera(minecraft, blockItem, stack, targetPos);
        if (state == null) {
            state = blockItem.getBlock().defaultBlockState();
        }
        state = applyRotation(state, controller.getPlaceRotateDegrees(), minecraft.level, targetPos);
        return PlacementStatePreset.apply(state, controller.getPlacementStatePreset());
    }

    public static BlockState resolveStateWithCamera(
            Minecraft minecraft, BlockItem blockItem, ItemStack stack, BlockPos targetPos) {
        if (minecraft.player == null || minecraft.level == null || targetPos == null) {
            return null;
        }
        Vec3 hit = Vec3.atCenterOf(targetPos);
        BlockHitResult result = new BlockHitResult(
                hit, minecraft.player.getDirection().getOpposite(), targetPos, false);
        return blockItem.getBlock().getStateForPlacement(
                new BlockPlaceContext(minecraft.level, minecraft.player, InteractionHand.MAIN_HAND, stack, result));
    }

    public static BlockState applyRotation(
            BlockState state, int rotateDegrees, LevelAccessor level, BlockPos pos) {
        for (int turns = (rotateDegrees / 90) & 3; turns > 0; turns--) {
            state = state.rotate(Rotation.CLOCKWISE_90);
        }
        return state;
    }
}

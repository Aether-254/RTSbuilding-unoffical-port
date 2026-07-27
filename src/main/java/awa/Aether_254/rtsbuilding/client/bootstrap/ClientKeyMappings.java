package awa.Aether_254.rtsbuilding.client.bootstrap;

import com.mojang.blaze3d.platform.InputConstants;
import awa.Aether_254.rtsbuilding.RtsbuildingMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

public final class ClientKeyMappings {
    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
                    RtsbuildingMod.MODID, "rtsbuilding"));
    private static final InputConstants.Key LEGACY_ROTATE_DRAG_DEFAULT =
            InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);
    private static final InputConstants.Key LEGACY_PAN_DRAG_DEFAULT =
            InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
    private static final InputConstants.Key DEFAULT_ROTATE_DRAG =
            InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_RIGHT);
    private static final InputConstants.Key DEFAULT_PAN_DRAG =
            InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_MIDDLE);

    public static final KeyMapping TOGGLE_RTS = new KeyMapping(
            "key.rtsbuilding.toggle_rts",
            GLFW.GLFW_KEY_G,
            CATEGORY);
    public static final KeyMapping QUICK_FUNNEL = new KeyMapping(
            "key.rtsbuilding.quick_funnel",
            GLFW.GLFW_KEY_F,
            CATEGORY);
    public static final KeyMapping QUICK_DROP = new KeyMapping(
            "key.rtsbuilding.quick_drop",
            GLFW.GLFW_KEY_Q,
            CATEGORY);
    public static final KeyMapping ROTATE_SHAPE = new KeyMapping(
            "key.rtsbuilding.rotate_shape",
            GLFW.GLFW_KEY_R,
            CATEGORY);
    public static final KeyMapping OPEN_CRAFT_TERMINAL = new KeyMapping(
            "key.rtsbuilding.open_craft_terminal",
            GLFW.GLFW_KEY_C,
            CATEGORY);
    public static final KeyMapping PIN_QUICK_SLOT = new KeyMapping(
            "key.rtsbuilding.pin_quick_slot",
            GLFW.GLFW_KEY_P,
            CATEGORY);
    public static final KeyMapping BLUEPRINT_CANCEL = new KeyMapping(
            "key.rtsbuilding.blueprint_cancel",
            GLFW.GLFW_KEY_X,
            CATEGORY);
    public static final KeyMapping DECREASE_SENSITIVITY = new KeyMapping(
            "key.rtsbuilding.decrease_sensitivity",
            GLFW.GLFW_KEY_LEFT_BRACKET,
            CATEGORY);
    public static final KeyMapping INCREASE_SENSITIVITY = new KeyMapping(
            "key.rtsbuilding.increase_sensitivity",
            GLFW.GLFW_KEY_RIGHT_BRACKET,
            CATEGORY);
    public static final KeyMapping MODE_INTERACT = new KeyMapping(
            "key.rtsbuilding.mode_interact",
            GLFW.GLFW_KEY_I,
            CATEGORY);
    public static final KeyMapping MODE_LINK_STORAGE = new KeyMapping(
            "key.rtsbuilding.mode_link_storage",
            GLFW.GLFW_KEY_L,
            CATEGORY);
    public static final KeyMapping MODE_ROTATE = new KeyMapping(
            "key.rtsbuilding.mode_rotate",
            GLFW.GLFW_KEY_R,
            CATEGORY);
    public static final KeyMapping MODE_FUNNEL = new KeyMapping(
            "key.rtsbuilding.mode_funnel",
            GLFW.GLFW_KEY_F,
            CATEGORY);
    public static final KeyMapping ACTION_PRIMARY = new KeyMapping(
            "key.rtsbuilding.action_primary",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            CATEGORY);
    public static final KeyMapping MOVE_PLAYER = new KeyMapping(
            "key.rtsbuilding.move_player",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            CATEGORY);
    public static final KeyMapping ACTION_BREAK = new KeyMapping(
            "key.rtsbuilding.action_break",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_LEFT,
            CATEGORY);
    public static final KeyMapping CONFIRM_BATCH_PLACE = new KeyMapping(
            "key.rtsbuilding.confirm_batch_place",
            GLFW.GLFW_KEY_ENTER,
            CATEGORY);
    public static final KeyMapping CONFIRM_BATCH_DESTROY = new KeyMapping(
            "key.rtsbuilding.confirm_batch_destroy",
            GLFW.GLFW_KEY_ENTER,
            CATEGORY);
    public static final KeyMapping CAMERA_ROTATE_DRAG = new KeyMapping(
            "key.rtsbuilding.camera_rotate_drag",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_RIGHT,
            CATEGORY);
    public static final KeyMapping CAMERA_PAN_DRAG = new KeyMapping(
            "key.rtsbuilding.camera_pan_drag",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
            CATEGORY);
    public static final KeyMapping PICK_BLOCK = new KeyMapping(
            "key.rtsbuilding.pick_block",
            InputConstants.Type.MOUSE,
            GLFW.GLFW_MOUSE_BUTTON_MIDDLE,
            CATEGORY);
    public static final KeyMapping CAMERA_UP = new KeyMapping(
            "key.rtsbuilding.camera_up",
            GLFW.GLFW_KEY_SPACE,
            CATEGORY);
    public static final KeyMapping CAMERA_UP_SECONDARY = new KeyMapping(
            "key.rtsbuilding.camera_up_secondary",
            GLFW.GLFW_KEY_UNKNOWN,
            CATEGORY);
    public static final KeyMapping CAMERA_DOWN = new KeyMapping(
            "key.rtsbuilding.camera_down_arrow",
            GLFW.GLFW_KEY_LEFT_SHIFT,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_FORWARD = new KeyMapping(
            "key.rtsbuilding.selection_nudge_forward",
            GLFW.GLFW_KEY_UP,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_BACK = new KeyMapping(
            "key.rtsbuilding.selection_nudge_back",
            GLFW.GLFW_KEY_DOWN,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_LEFT = new KeyMapping(
            "key.rtsbuilding.selection_nudge_left",
            GLFW.GLFW_KEY_LEFT,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_RIGHT = new KeyMapping(
            "key.rtsbuilding.selection_nudge_right",
            GLFW.GLFW_KEY_RIGHT,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_UP = new KeyMapping(
            "key.rtsbuilding.selection_nudge_up",
            GLFW.GLFW_KEY_PAGE_UP,
            CATEGORY);
    public static final KeyMapping SELECTION_NUDGE_DOWN = new KeyMapping(
            "key.rtsbuilding.selection_nudge_down",
            GLFW.GLFW_KEY_PAGE_DOWN,
            CATEGORY);

    private ClientKeyMappings() {
    }

    public static void register() {
        for (KeyMapping mapping : new KeyMapping[] {
                TOGGLE_RTS, QUICK_FUNNEL, QUICK_DROP, ROTATE_SHAPE, OPEN_CRAFT_TERMINAL,
                PIN_QUICK_SLOT, BLUEPRINT_CANCEL, DECREASE_SENSITIVITY, INCREASE_SENSITIVITY,
                MODE_INTERACT, MODE_LINK_STORAGE, MODE_ROTATE, MODE_FUNNEL, ACTION_PRIMARY,
                MOVE_PLAYER, ACTION_BREAK, CONFIRM_BATCH_PLACE, CONFIRM_BATCH_DESTROY,
                CAMERA_ROTATE_DRAG, CAMERA_PAN_DRAG, PICK_BLOCK, CAMERA_UP, CAMERA_UP_SECONDARY,
                CAMERA_DOWN, SELECTION_NUDGE_FORWARD, SELECTION_NUDGE_BACK, SELECTION_NUDGE_LEFT,
                SELECTION_NUDGE_RIGHT, SELECTION_NUDGE_UP, SELECTION_NUDGE_DOWN }) {
            KeyBindingHelper.registerKeyBinding(mapping);
        }
    }
}

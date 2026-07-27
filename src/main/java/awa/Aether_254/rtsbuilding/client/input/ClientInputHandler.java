package awa.Aether_254.rtsbuilding.client.input;


import awa.Aether_254.rtsbuilding.RtsbuildingMod;
import awa.Aether_254.rtsbuilding.client.bootstrap.ClientKeyMappings;
import awa.Aether_254.rtsbuilding.client.controller.ClientRtsController;
import awa.Aether_254.rtsbuilding.client.network.RtsClientPacketGateway;
import awa.Aether_254.rtsbuilding.client.pathfinding.RtsClientPathfinding;
import net.minecraft.client.Minecraft;

public final class ClientInputHandler {
    private static boolean toggleKeyWasDown = false;
    private static int toggleCooldownTicks = 0;

    private ClientInputHandler() {
    }

    public static void onClientTickPre() {
        ClientRtsController.get().preTick();
        RtsClientPathfinding.tickPre();
    }

    public static void onClientTickPost() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            toggleKeyWasDown = false;
            toggleCooldownTicks = 0;
            ClientRtsController.get().tick();
            return;
        }

        if (toggleCooldownTicks > 0) {
            toggleCooldownTicks--;
        }

        boolean toggleKeyDown = ClientKeyMappings.TOGGLE_RTS.isDown();
        if (!toggleKeyDown && toggleKeyWasDown && toggleCooldownTicks == 0) {
            RtsClientPacketGateway.sendToggleCamera(ClientRtsController.get().isStartCameraAtPlayerHead());
            toggleCooldownTicks = 6;
        }
        toggleKeyWasDown = toggleKeyDown;

        ClientRtsController.get().tick();
    }
}

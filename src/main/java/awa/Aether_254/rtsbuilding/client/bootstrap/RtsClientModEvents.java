package awa.Aether_254.rtsbuilding.client.bootstrap;


import awa.Aether_254.rtsbuilding.RtsbuildingMod;
import awa.Aether_254.rtsbuilding.client.pathfinding.RtsMovementModeRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RtsbuildingMod.MODID, value = Dist.CLIENT)
public final class RtsClientModEvents {
    private RtsClientModEvents() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Initialise the built-in movement mode handlers and fire the registration
        // event so other mods can register custom movement modes.
        RtsMovementModeRegistry.init();
        RtsMovementModeRegistry.fireRegistrationEvent();

        RtsbuildingMod.LOGGER.info("HELLO FROM CLIENT SETUP");
        RtsbuildingMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}

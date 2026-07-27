package awa.Aether_254.rtsbuilding.network.pathfinding;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * Registers RTS pathfinding C2S packet.
 */
public final class RtsPathfindingPackets {

    private RtsPathfindingPackets() {}

    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(
                C2SRtsPathfindingPayload.TYPE,
                C2SRtsPathfindingPayload.STREAM_CODEC,
                RtsPathfindingNetworkHandlers::handlePathfinding);
    }
}

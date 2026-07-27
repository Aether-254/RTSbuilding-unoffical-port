package awa.Aether_254.rtsbuilding.server.api.impl;

import awa.Aether_254.rtsbuilding.api.RtsSessionQueryAPI;
import awa.Aether_254.rtsbuilding.common.build.BuilderMode;
import awa.Aether_254.rtsbuilding.server.service.ServiceRegistry;
import net.minecraft.server.level.ServerPlayer;

/**
 * {@link RtsSessionQueryAPI} 的实现——委托给会话查询服务层。
 */
public final class RtsSessionQueryAPIImpl implements RtsSessionQueryAPI {

    private static final ServiceRegistry REGISTRY = ServiceRegistry.getInstance();

    @Override
    public BuilderMode getMode(ServerPlayer player) {
        return REGISTRY.session().getMode(player);
    }
}

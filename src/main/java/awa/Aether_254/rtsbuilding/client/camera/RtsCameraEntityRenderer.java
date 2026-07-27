package awa.Aether_254.rtsbuilding.client.camera;

import awa.Aether_254.rtsbuilding.common.entity.RtsCameraEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public final class RtsCameraEntityRenderer extends EntityRenderer<RtsCameraEntity> {
    public RtsCameraEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public Identifier getTextureLocation(RtsCameraEntity entity) {
        return Identifier.withDefaultNamespace("textures/misc/empty.png");
    }

    @Override
    public boolean shouldRender(RtsCameraEntity livingEntity, net.minecraft.client.renderer.culling.Frustum camera,
            double camX, double camY, double camZ) {
        return false;
    }
}

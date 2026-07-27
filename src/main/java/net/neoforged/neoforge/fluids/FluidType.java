package net.neoforged.neoforge.fluids;

import java.util.function.Consumer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

public class FluidType {
    public static final int BUCKET_VOLUME = 1000;
    protected final Properties properties;

    public FluidType(Properties properties) {
        this.properties = properties;
    }

    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
    }

    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return false;
    }

    public static class Properties {
        public static Properties create() { return new Properties(); }
        public Properties motionScale(double ignored) { return this; }
        public Properties fallDistanceModifier(float ignored) { return this; }
        public Properties density(int ignored) { return this; }
        public Properties viscosity(int ignored) { return this; }
        public Properties lightLevel(int ignored) { return this; }
        public Properties sound(Object action, Object sound) { return this; }
    }
}

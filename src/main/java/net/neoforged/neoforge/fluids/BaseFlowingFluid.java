package net.neoforged.neoforge.fluids;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class BaseFlowingFluid extends FlowingFluid {
    protected final Properties properties;

    protected BaseFlowingFluid(Properties properties) {
        this.properties = properties;
    }

    @Override public Fluid getFlowing() { return properties.flowing.get(); }
    @Override public Fluid getSource() { return properties.source.get(); }
    @Override public Item getBucket() { return properties.bucket == null ? Items.AIR : properties.bucket.get(); }
    @Override protected boolean canConvertToSource(ServerLevel level) { return false; }
    @Override protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        if (level instanceof Level actualLevel)
            Block.dropResources(state, actualLevel, pos);
    }
    @Override protected int getSlopeFindDistance(LevelReader level) { return properties.slopeFindDistance; }
    @Override protected int getDropOff(LevelReader level) { return properties.levelDecreasePerBlock; }
    @Override public int getTickDelay(LevelReader level) { return properties.tickRate; }
    @Override protected float getExplosionResistance() { return properties.explosionResistance; }
    @Override protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid,
        Direction direction) { return direction == Direction.DOWN && !isSame(fluid); }
    @Override protected BlockState createLegacyBlock(FluidState state) {
        if (properties.block == null) return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
        return properties.block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    public static class Source extends BaseFlowingFluid {
        public Source(Properties properties) { super(properties); }
        @Override public int getAmount(FluidState state) { return 8; }
        @Override public boolean isSource(FluidState state) { return true; }
    }

    public static class Flowing extends BaseFlowingFluid {
        public Flowing(Properties properties) {
            super(properties);
            registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
        }
        @Override protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }
        @Override public int getAmount(FluidState state) { return state.getValue(LEVEL); }
        @Override public boolean isSource(FluidState state) { return false; }
    }

    public static class Properties {
        private final Supplier<? extends Fluid> source;
        private final Supplier<? extends Fluid> flowing;
        private Supplier<? extends Item> bucket;
        private Supplier<? extends Block> block;
        private int levelDecreasePerBlock = 1;
        private int tickRate = 5;
        private int slopeFindDistance = 4;
        private float explosionResistance = 1;

        public Properties(Supplier<?> ignoredType, Supplier<? extends Fluid> source,
            Supplier<? extends Fluid> flowing) {
            this.source = source;
            this.flowing = flowing;
        }
        public Properties bucket(Supplier<? extends Item> bucket) { this.bucket = bucket; return this; }
        public Properties block(Supplier<? extends Block> block) { this.block = block; return this; }
        public Properties levelDecreasePerBlock(int value) { this.levelDecreasePerBlock = value; return this; }
        public Properties tickRate(int value) { this.tickRate = value; return this; }
        public Properties slopeFindDistance(int value) { this.slopeFindDistance = value; return this; }
        public Properties explosionResistance(float value) { this.explosionResistance = value; return this; }
    }
}

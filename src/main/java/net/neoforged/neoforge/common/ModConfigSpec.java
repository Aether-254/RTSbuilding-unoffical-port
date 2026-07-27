package net.neoforged.neoforge.common;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.tuple.Pair;

public final class ModConfigSpec {
    public void save() {
    }

    public static final class Builder {
		public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> factory) {
			T value = factory.apply(this);
			return Pair.of(value, build());
		}

		public Builder push(String ignored) {
			return this;
		}

		public Builder pop() {
			return this;
		}

        public Builder comment(String... ignored) {
            return this;
        }

        public Builder translation(String ignored) {
            return this;
        }

        public BooleanValue define(String path, boolean value) {
            return new BooleanValue(value);
        }

        public IntValue defineInRange(String path, int value, int min, int max) {
            return new IntValue(value, min, max);
        }

        public LongValue defineInRange(String path, long value, long min, long max) {
            return new LongValue(value, min, max);
        }

        public DoubleValue defineInRange(String path, double value, double min, double max) {
            return new DoubleValue(value, min, max);
        }

        public <E extends Enum<E>> EnumValue<E> defineEnum(String path, E value) {
            return new EnumValue<>(value);
        }

		public <T> ConfigValue<List<? extends T>> defineListAllowEmpty(String path, List<? extends T> defaults,
			Predicate<Object> validator) {
			return new ConfigValue<>(defaults);
		}

        public ModConfigSpec build() {
            return new ModConfigSpec();
        }
    }

    public static class ConfigValue<T> {
        protected T value;

        ConfigValue(T value) {
            this.value = value;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
        }
    }

    public static final class BooleanValue extends ConfigValue<Boolean> {
        BooleanValue(boolean value) {
            super(value);
        }

        public boolean getAsBoolean() {
            return value;
        }
    }

    public static final class IntValue extends ConfigValue<Integer> {
        private final int min;
        private final int max;

        IntValue(int value, int min, int max) {
            super(value);
            this.min = min;
            this.max = max;
        }

        @Override
        public void set(Integer value) {
            super.set(Math.max(min, Math.min(max, value)));
        }

        public int getAsInt() {
            return value;
        }

        public int intValue() {
            return value;
        }
    }

    public static final class LongValue extends ConfigValue<Long> {
        LongValue(long value, long min, long max) {
            super(Math.max(min, Math.min(max, value)));
        }

        public long longValue() {
            return value;
        }
    }

    public static final class DoubleValue extends ConfigValue<Double> {
        DoubleValue(double value, double min, double max) {
            super(Math.max(min, Math.min(max, value)));
        }

        public double getAsDouble() {
            return value;
        }

        public double doubleValue() {
            return value;
        }
    }

    public static final class EnumValue<E extends Enum<E>> extends ConfigValue<E> {
        EnumValue(E value) {
            super(value);
        }
    }
}

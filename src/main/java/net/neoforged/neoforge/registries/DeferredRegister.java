package net.neoforged.neoforge.registries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import net.minecraft.core.component.DataComponentType;

public class DeferredRegister<T> {
    private final ResourceKey<? extends Registry<T>> registryKey;
    private final Registry<T> directRegistry;
    private final String namespace;
    private final Map<String, DeferredHolder<T, ? extends T>> entries = new LinkedHashMap<>();
    private boolean registered;

    private DeferredRegister(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        this.registryKey = registryKey;
        this.directRegistry = null;
        this.namespace = namespace;
    }

    private DeferredRegister(Registry<T> registry, String namespace) {
        this.registryKey = null;
        this.directRegistry = registry;
        this.namespace = namespace;
    }

    public static <T> DeferredRegister<T> create(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        return new DeferredRegister<>(registryKey, namespace);
    }

    public static <T> DeferredRegister<T> create(Registry<T> registry, String namespace) {
        return new DeferredRegister<>(registry, namespace);
    }

    public static DataComponents createDataComponents(
        ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String namespace) {
        return new DataComponents(registryKey, namespace);
    }

    public <I extends T> DeferredHolder<T, I> register(String path, Supplier<? extends I> factory) {
        DeferredHolder<T, I> holder = new DeferredHolder<>(Identifier.fromNamespaceAndPath(namespace, path), factory);
        entries.put(path, holder);
        return holder;
    }

    public void register(IEventBus ignored) {
        registerAll();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void registerAll() {
        if (registered) {
            return;
        }
        registered = true;
        Registry registry = resolveRegistry();
        entries.forEach((path, holder) -> {
            T value = ((DeferredHolder<T, T>) holder).create();
            if (registry != null)
                Registry.register(registry, Identifier.fromNamespaceAndPath(namespace, path), value);
            ((DeferredHolder<T, T>) holder).bind(value);
        });
    }

    public Collection<DeferredHolder<T, ? extends T>> getEntries() {
        return entries.values();
    }

    @SuppressWarnings("unchecked")
    private Registry<T> resolveRegistry() {
        if (directRegistry != null) return directRegistry;
        if (Registries.ITEM.equals(registryKey)) return (Registry<T>) BuiltInRegistries.ITEM;
        if (Registries.BLOCK.equals(registryKey)) return (Registry<T>) BuiltInRegistries.BLOCK;
        if (Registries.ENTITY_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.ENTITY_TYPE;
        if (Registries.CREATIVE_MODE_TAB.equals(registryKey)) return (Registry<T>) BuiltInRegistries.CREATIVE_MODE_TAB;
        if (Registries.BLOCK_ENTITY_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.BLOCK_ENTITY_TYPE;
        if (Registries.MENU.equals(registryKey)) return (Registry<T>) BuiltInRegistries.MENU;
        if (Registries.PARTICLE_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.PARTICLE_TYPE;
        if (Registries.POINT_OF_INTEREST_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.POINT_OF_INTEREST_TYPE;
        if (Registries.RECIPE_SERIALIZER.equals(registryKey)) return (Registry<T>) BuiltInRegistries.RECIPE_SERIALIZER;
        if (Registries.RECIPE_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.RECIPE_TYPE;
        if (Registries.MOB_EFFECT.equals(registryKey)) return (Registry<T>) BuiltInRegistries.MOB_EFFECT;
        if (Registries.POTION.equals(registryKey)) return (Registry<T>) BuiltInRegistries.POTION;
        if (Registries.DATA_COMPONENT_TYPE.equals(registryKey)) return (Registry<T>) BuiltInRegistries.DATA_COMPONENT_TYPE;
        if (Registries.FLUID.equals(registryKey)) return (Registry<T>) BuiltInRegistries.FLUID;
        return null;
    }

    public static final class DataComponents extends DeferredRegister<DataComponentType<?>> {
        private DataComponents(ResourceKey<? extends Registry<DataComponentType<?>>> registryKey, String namespace) {
            super(registryKey, namespace);
        }

        public <D> DeferredHolder<DataComponentType<?>, DataComponentType<D>> registerComponentType(String path,
            UnaryOperator<DataComponentType.Builder<D>> builderOperator) {
            return register(path, () -> builderOperator.apply(DataComponentType.builder()).build());
        }
    }
}

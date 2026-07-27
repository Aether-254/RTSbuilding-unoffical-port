package net.neoforged.neoforge.registries;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import net.minecraft.resources.Identifier;

public final class DeferredHolder<R, T extends R> implements Supplier<T>, Holder<R> {
    private final Identifier id;
    private final Supplier<? extends T> factory;
    private T value;

    DeferredHolder(Identifier id, Supplier<? extends T> factory) {
        this.id = id;
        this.factory = factory;
    }


    public Identifier getId() {
        return id;
    }

    public boolean isPresent() {
        return value != null;
    }
    void bind(T value) {
        this.value = value;
    }

    T create() {
        DeferredRegisterContext.set(id);
        try {
            return factory.get();
        } finally {
            DeferredRegisterContext.clear();
        }
    }

    @Override
    public T get() {
        if (value == null) {
            throw new IllegalStateException("Deferred value has not been registered yet");
        }
        return value;
    }
    private Holder<R> delegate() {
        return Holder.direct(get());
    }

    @Override
    public R value() { return get(); }

    @Override
    public boolean isBound() { return value != null; }

    @Override
    public boolean is(Identifier id) { return this.id.equals(id); }

    @Override
    public boolean is(ResourceKey<R> key) { return key.identifier().equals(id); }

    @Override
    public boolean is(Predicate<ResourceKey<R>> predicate) {
        return unwrapKey().filter(predicate).isPresent();
    }

    @Override
    public boolean is(TagKey<R> tag) { return delegate().is(tag); }

    @Override
    public boolean is(Holder<R> holder) { return holder == this || holder.value() == value(); }

    @Override
    public Stream<TagKey<R>> tags() { return Stream.empty(); }

    @Override
    public Either<ResourceKey<R>, R> unwrap() { return Either.right(value()); }

    @Override
    public Optional<ResourceKey<R>> unwrapKey() { return Optional.empty(); }

    @Override
    public Kind kind() { return Kind.DIRECT; }

    @Override
    public boolean canSerializeIn(HolderOwner<R> owner) { return true; }
}

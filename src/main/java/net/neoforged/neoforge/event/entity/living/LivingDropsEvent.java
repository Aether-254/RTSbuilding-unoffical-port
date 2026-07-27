package net.neoforged.neoforge.event.entity.living;

import java.util.Collection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;

public record LivingDropsEvent(LivingEntity entity, DamageSource source, Collection<ItemEntity> drops) {
	public LivingEntity getEntity() { return entity; }
	public DamageSource getSource() { return source; }
	public Collection<ItemEntity> getDrops() { return drops; }
}

package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public final class LivingDamageEvent {
	private LivingDamageEvent() {}

	public static final class Pre {
		private final LivingEntity entity;
		private final DamageSource source;
		private float newDamage;

		public Pre(LivingEntity entity, DamageSource source, float damage) {
			this.entity = entity;
			this.source = source;
			this.newDamage = damage;
		}

		public LivingEntity getEntity() { return entity; }
		public DamageSource getSource() { return source; }
		public float getNewDamage() { return newDamage; }
		public void setNewDamage(float damage) { newDamage = damage; }
	}
}

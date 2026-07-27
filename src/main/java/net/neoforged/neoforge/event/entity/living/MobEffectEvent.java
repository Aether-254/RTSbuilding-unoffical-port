package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public final class MobEffectEvent {
	private MobEffectEvent() {}

	public static final class Applicable {
		public enum Result { DEFAULT, APPLY, DO_NOT_APPLY }
		private final LivingEntity entity;
		private final MobEffectInstance effect;
		private Result result = Result.DEFAULT;

		public Applicable(LivingEntity entity, MobEffectInstance effect) {
			this.entity = entity;
			this.effect = effect;
		}
		public LivingEntity getEntity() { return entity; }
		public MobEffectInstance getEffectInstance() { return effect; }
		public void setResult(Result result) { this.result = result; }
		public Result getResult() { return result; }
	}
}

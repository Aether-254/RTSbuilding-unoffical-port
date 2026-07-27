package net.neoforged.neoforge.event.entity.player;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class PlayerInteractEvent {
	private final Player player;
	private final InteractionHand hand;
	private final Level level;
	private boolean canceled;
	private InteractionResult cancellationResult = InteractionResult.PASS;

	protected PlayerInteractEvent(Player player, InteractionHand hand, Level level) {
		this.player = player;
		this.hand = hand;
		this.level = level;
	}
	public Player getEntity() { return player; }
	public InteractionHand getHand() { return hand; }
	public Level getLevel() { return level; }
	public ItemStack getItemStack() { return player.getItemInHand(hand); }
	public void setCanceled(boolean canceled) { this.canceled = canceled; }
	public boolean isCanceled() { return canceled; }
	public void setCancellationResult(InteractionResult result) { cancellationResult = result; }
	public InteractionResult getCancellationResult() { return cancellationResult; }

	public static class EntityInteract extends PlayerInteractEvent {
		private final Entity target;
		public EntityInteract(Player player, InteractionHand hand, Level level, Entity target) {
			super(player, hand, level); this.target = target;
		}
		public Entity getTarget() { return target; }
	}

	public static final class EntityInteractSpecific extends EntityInteract {
		public EntityInteractSpecific(Player player, InteractionHand hand, Level level, Entity target) {
			super(player, hand, level, target);
		}
	}

	public static final class RightClickBlock extends PlayerInteractEvent {
		private final BlockPos pos;
		public RightClickBlock(Player player, InteractionHand hand, Level level, BlockPos pos) {
			super(player, hand, level); this.pos = pos;
		}
		public BlockPos getPos() { return pos; }
	}

	public static final class LeftClickBlock extends PlayerInteractEvent {
		public enum Action { START, STOP, ABORT }
		private final BlockPos pos;
		private final Action action;
		public LeftClickBlock(Player player, InteractionHand hand, Level level, BlockPos pos, Action action) {
			super(player, hand, level); this.pos = pos; this.action = action;
		}
		public BlockPos getPos() { return pos; }
		public Action getAction() { return action; }
	}
}

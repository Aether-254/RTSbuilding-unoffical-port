package awa.Aether_254.rtsbuilding.compat.ftb;

import net.minecraft.server.level.ServerPlayer;

public final class RtsFtbCompat {
    private RtsFtbCompat() {}

    public static boolean isDetectAvailable() { return false; }
    public static QuestDetectResult detectNow(ServerPlayer player) { return QuestDetectResult.unavailable(); }
    public static String progressionTeamKey(ServerPlayer player) { return player.getUUID().toString(); }
    public static String progressionTeamLabel(ServerPlayer player) { return player.getGameProfile().name(); }

    public record QuestDetectResult(boolean available, boolean error, int scannedTasks, int newlyCompletedTasks) {
        public static QuestDetectResult unavailable() { return new QuestDetectResult(false, false, 0, 0); }
        public static QuestDetectResult failed() { return new QuestDetectResult(true, true, 0, 0); }
        public static QuestDetectResult complete(int scanned, int completed) {
            return new QuestDetectResult(true, false, Math.max(0, scanned), Math.max(0, completed));
        }
    }
}

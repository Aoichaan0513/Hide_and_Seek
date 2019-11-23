package org.aoichaan0513.hide_and_seek.Timer;

import org.aoichaan0513.hide_and_seek.API.GameManager;
import org.aoichaan0513.hide_and_seek.Main;
import org.bukkit.scheduler.BukkitTask;

public class Timer {
    private static TimerRunnable runner = null;
    private static BukkitTask timer = null;

    public static void startTimer(int setting_countdown, int setting_gametime) {
        if (timer == null) {
            GameManager.setGameState(GameManager.GameState.READY);
            runner = new TimerRunnable(setting_countdown, setting_gametime);
            timer = runner.runTaskTimer(Main.getInstance(), 0L, 20L);
            return;
        }
        return;
    }

    public static void endTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            runner = null;
            GameManager.setGameState(GameManager.GameState.NONE);
            return;
        }
        return;
    }
}

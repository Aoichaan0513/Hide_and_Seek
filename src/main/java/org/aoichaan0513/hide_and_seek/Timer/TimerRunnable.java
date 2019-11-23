package org.aoichaan0513.hide_and_seek.Timer;

import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.FallingBlockDisguise;
import org.aoichaan0513.hide_and_seek.API.ActionBarManager;
import org.aoichaan0513.hide_and_seek.API.GameManager;
import org.aoichaan0513.hide_and_seek.API.Teams;
import org.aoichaan0513.hide_and_seek.API.TimerFormat;
import org.aoichaan0513.hide_and_seek.Listeners.onMove;
import org.aoichaan0513.hide_and_seek.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerRunnable extends BukkitRunnable {

    public static int countDown;
    public static int gameTime;

    public TimerRunnable(int countDown, int gameTime) {
        this.countDown = countDown;
        this.gameTime = gameTime;
    }

    @Override
    public void run() {
        if (GameManager.isGame(GameManager.GameState.NONE)) return;

        if (countDown < 1) {
            gameTime--;

            for (Map.Entry<UUID, Location> entry : onMove.getHashMap().entrySet())
                for (Player player : Bukkit.getOnlinePlayers())
                    if (player.getUniqueId() != entry.getKey())
                        player.sendBlockChange(entry.getValue(), Material.SMOOTH_BRICK, (byte) 0);

            if (gameTime < 1) {
                sendTitle("ゲーム終了");
                Timer.endTimer();

                Main.getDisguise().undisguiseAll();

                for (Player player : Bukkit.getOnlinePlayers()) {
                    HashMap<UUID, Location> hashMap = onMove.getHashMap();

                    if (hashMap.containsKey(player.getUniqueId())) {
                        for (Player p : Bukkit.getOnlinePlayers())
                            p.sendBlockChange(hashMap.get(player.getUniqueId()), Material.AIR, (byte) 0);

                        hashMap.remove(player.getUniqueId());
                    }
                }
            }
            if (gameTime < 16) {
                ActionBarManager.sendActionBar("" + ChatColor.YELLOW + "ゲーム終了まで" + ChatColor.RESET + ChatColor.YELLOW + "残り" + ChatColor.GOLD + ChatColor.UNDERLINE + gameTime + ChatColor.RESET + ChatColor.YELLOW + "秒");
                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 1);
            }
            if (gameTime == 60) {
                ActionBarManager.sendActionBar("" + ChatColor.YELLOW + "ゲーム終了まで" + ChatColor.RESET + ChatColor.YELLOW + "残り" + ChatColor.GOLD + ChatColor.UNDERLINE + "1" + ChatColor.RESET + ChatColor.YELLOW + "分");
                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 1);
            }

            ActionBarManager.sendActionBar("" + ChatColor.YELLOW + "ゲーム終了まで" + ChatColor.RESET + ChatColor.YELLOW + "残り" + ChatColor.GOLD + ChatColor.UNDERLINE + TimerFormat.formatJapan(gameTime));
        } else {
            countDown--;

            for (Map.Entry<UUID, Location> entry : onMove.getHashMap().entrySet())
                for (Player player : Bukkit.getOnlinePlayers())
                    if (player.getUniqueId() != entry.getKey())
                        player.sendBlockChange(entry.getValue(), Material.SMOOTH_BRICK, (byte) 0);

            if (countDown < 1) {
                sendTitle("ゲーム開始");

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 1);

                    if (Teams.isTeamJoined(player, Teams.TeamType.HIDER)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200000, 0, false, false));

                        FallingBlockDisguise d = (FallingBlockDisguise) DisguiseType.FALLING_BLOCK.newInstance();
                        d.setMaterial(Material.SMOOTH_BRICK);
                        Main.getDisguise().disguise(player, d);
                    }
                }
            }
            if (countDown < 16) {
                ActionBarManager.sendActionBar("" + ChatColor.YELLOW + "ゲーム開始まで" + ChatColor.RESET + ChatColor.YELLOW + "残り" + ChatColor.GOLD + ChatColor.UNDERLINE + countDown + ChatColor.RESET + ChatColor.YELLOW + "秒");
                for (Player player : Bukkit.getOnlinePlayers())
                    player.playSound(player.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 1);
            }
        }
    }

    private void sendTitle(String title) {
        new BukkitRunnable() {

            private int i = 0;

            @Override
            public void run() {
                if (i <= title.length()) {
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendTitle("" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.UNDERLINE + title.substring(0, i), ChatColor.GREEN + "Hide" + ChatColor.GRAY + " and " + ChatColor.RED + "Seek");
                    i++;
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 5, 5);
    }

    public static int getTime() {
        if (countDown <= 0) {
            return gameTime;
        } else {
            return countDown;
        }
    }

    public static int getCountdown() {
        return countDown;
    }

    public static void setCountdown(int countDown) {
        TimerRunnable.countDown = countDown;
    }

    public static int getGametime() {
        return gameTime;
    }

    public static void setGametime(int gameTime) {
        TimerRunnable.gameTime = gameTime;
    }

    public static void addGametime(int gameTime) {
        TimerRunnable.gameTime += gameTime;
    }

    public static void removeGametime(int gameTime) {
        TimerRunnable.gameTime -= gameTime;
    }
}

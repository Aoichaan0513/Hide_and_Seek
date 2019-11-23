package org.aoichaan0513.hide_and_seek.Listeners;

import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.FallingBlockDisguise;
import org.aoichaan0513.hide_and_seek.API.ActionBarManager;
import org.aoichaan0513.hide_and_seek.API.GameManager;
import org.aoichaan0513.hide_and_seek.API.Teams;
import org.aoichaan0513.hide_and_seek.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class onMove implements Listener {

    private static HashMap<UUID, Location> hashMap = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location locFrom = e.getFrom();
        Location locTo = e.getTo();

        if (GameManager.isGame(GameManager.GameState.NONE)) return;

        if (locFrom.getBlockX() == locTo.getBlockX() && locFrom.getBlockY() == locTo.getBlockY() && locFrom.getBlockZ() == locTo.getBlockZ()
                && (locFrom.getYaw() != locTo.getYaw() || locFrom.getPitch() != locTo.getPitch()))
            return;

        if (Teams.isTeamJoined(p, Teams.TeamType.HIDER)) {
            FallingBlockDisguise d = (FallingBlockDisguise) DisguiseType.FALLING_BLOCK.newInstance();
            d.setMaterial(Material.SMOOTH_BRICK);
            Main.getDisguise().disguise(p, d);

            if (hashMap.containsKey(p.getUniqueId())) {
                ActionBarManager.sendActionBar(p, "" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "ブロックが破壊されました。");

                for (Player player : Bukkit.getOnlinePlayers())
                    player.sendBlockChange(hashMap.get(p.getUniqueId()), Material.AIR, (byte) 0);
                hashMap.remove(p.getUniqueId());
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    Location loc = p.getLocation();

                    if (locTo.getBlockX() == loc.getBlockX() && locTo.getBlockY() == loc.getBlockY() && locTo.getBlockZ() == loc.getBlockZ() && p.isOnGround()) {
                        if (isNotGroundPlayer(loc) && loc.getBlock().getType() == Material.AIR) {
                            ActionBarManager.sendActionBar(p, "" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "ブロックを設置しました。");

                            Main.getDisguise().undisguise(p);

                            /*
                            PacketContainer testPacket = new PacketContainer(PacketType.Play.Client.USE_ITEM);
                            testPacket.getBlocks().write(0, Material.SMOOTH_BRICK);

                            try {
                                Main.getProtocolManager().sendServerPacket(p, testPacket);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException("Cannot send packet " + testPacket, e);
                            }
                            */

                            for (Player player : Bukkit.getOnlinePlayers())
                                if (player.getUniqueId() != p.getUniqueId())
                                    player.sendBlockChange(loc, Material.SMOOTH_BRICK, (byte) 0);

                            hashMap.put(p.getUniqueId(), loc);
                            return;
                        } else {
                            ActionBarManager.sendActionBar(p, "" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "今立っている場所にはブロックを設置できません。");
                            return;
                        }
                    }
                }
            }.runTaskLater(Main.getInstance(), 20 * 3);
        }
    }

    public static HashMap<UUID, Location> getHashMap() {
        return hashMap;
    }

    private boolean isNotGroundPlayer(Location loc) {
        for (Map.Entry<UUID, Location> entry : hashMap.entrySet()) {
            Location locPlayer = entry.getValue();

            if (locPlayer.getBlockX() == loc.getBlockX() && (locPlayer.getBlockY() == (loc.getBlockY() - 1) || locPlayer.getBlockY() == loc.getBlockY()) && locPlayer.getBlockZ() == loc.getBlockZ())
                return false;
        }
        return true;
    }
}

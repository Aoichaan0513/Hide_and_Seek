package org.aoichaan0513.hide_and_seek.Listeners;

import org.aoichaan0513.hide_and_seek.API.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.UUID;

public class onDamage implements Listener {

    @EventHandler
    public void onDamegeByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player p = (Player) e.getEntity();

            if (Teams.isTeamJoined(damager, Teams.TeamType.SEEKER) && Teams.isTeamJoined(p, Teams.TeamType.HIDER)) {
                HashMap<UUID, Location> hashMap = onMove.getHashMap();

                if (hashMap.containsKey(p.getUniqueId())) {
                    for (Player player : Bukkit.getOnlinePlayers())
                        player.sendBlockChange(hashMap.get(p.getUniqueId()), Material.AIR, (byte) 0);
                    hashMap.remove(p.getUniqueId());
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }
    }
}

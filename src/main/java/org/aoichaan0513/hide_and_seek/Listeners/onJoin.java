package org.aoichaan0513.hide_and_seek.Listeners;

import org.aoichaan0513.hide_and_seek.API.Teams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class onJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!Teams.isTeamJoined(p))
            Teams.joinTeam(p, Teams.TeamType.HIDER);
    }
}

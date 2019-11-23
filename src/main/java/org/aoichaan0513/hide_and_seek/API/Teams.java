package org.aoichaan0513.hide_and_seek.API;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Teams {

    private static Team Team_hider;
    private static Team Team_seeker;

    public static void setScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        Team_hider = board.getTeam("Hider");
        if (Team_hider == null) {
            Team_hider = board.registerNewTeam("Hider");
            Team_hider.setPrefix(ChatColor.AQUA.toString());
            Team_hider.setSuffix(ChatColor.RESET.toString());
            Team_hider.setDisplayName("Hider");
            Team_hider.setAllowFriendlyFire(false);
            Team_hider.setCanSeeFriendlyInvisibles(true);
            Team_hider.setNameTagVisibility(NameTagVisibility.NEVER);
        }

        Team_seeker = board.getTeam("Seeker");
        if (Team_seeker == null) {
            Team_seeker = board.registerNewTeam("Seeker");
            Team_seeker.setPrefix(ChatColor.RED.toString());
            Team_seeker.setSuffix(ChatColor.RESET.toString());
            Team_seeker.setDisplayName("Seeker");
            Team_seeker.setAllowFriendlyFire(false);
            Team_seeker.setCanSeeFriendlyInvisibles(true);
            Team_seeker.setNameTagVisibility(NameTagVisibility.NEVER);
        }
    }

    public static void resetScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();

        Team_hider.unregister();
        Team_seeker.unregister();

        setScoreboard();
    }


    public static Team getTeam(TeamType team) {
        return team.getTeam();
    }

    public static boolean isTeamJoined(Player p) {
        for (TeamType team : TeamType.values()) {
            return isTeamJoined(p, team);
        }
        return false;
    }

    public static boolean isTeamJoined(Player p, TeamType team) {
        return team.getTeam().hasEntry(p.getName());
    }

    public static void joinTeam(Player p, TeamType team) {
        team.getTeam().addEntry(p.getName());
    }

    public static void leaveTeam(Player p, TeamType team) {
        team.getTeam().removeEntry(p.getName());
    }

    public static void leaveTeam(Player p) {
        for (TeamType team : TeamType.values()) {
            if (isTeamJoined(p, team)) {
                leaveTeam(p, team);
            }
        }
    }

    public enum TeamType {
        HIDER(Team_hider),
        SEEKER(Team_seeker);

        private final Team team;

        private TeamType(Team team) {
            this.team = team;
        }

        public Team getTeam() {
            return team;
        }
    }
}

package org.aoichaan0513.hide_and_seek.Commands.Command;

import org.aoichaan0513.hide_and_seek.API.GameManager;
import org.aoichaan0513.hide_and_seek.API.MainAPI;
import org.aoichaan0513.hide_and_seek.API.Teams;
import org.aoichaan0513.hide_and_seek.Commands.ICommand;
import org.aoichaan0513.hide_and_seek.Timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GEnd extends ICommand {

    public GEnd(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (!GameManager.isGame(GameManager.GameState.NONE)) {
            Bukkit.broadcastMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "ゲーム終了");
            Timer.endTimer();
        }

        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "データをリセットしています…");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "あなたのデータをリセットしています…");
            player.setGameMode(GameMode.ADVENTURE);
            Teams.leaveTeam(player);
            Teams.joinTeam(player, Teams.TeamType.HIDER);
            player.teleport(player.getWorld().getSpawnLocation());
            player.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SUCCESS) + ChatColor.BOLD + ChatColor.UNDERLINE + Bukkit.getOnlinePlayers().size() + "人" + ChatColor.RESET + ChatColor.GREEN + "のデータをリセットしました。");
            continue;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "データをリセットしています…");
        return;
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {

    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {

    }

    @Override
    public List<String> onPlayerTabComplete(Player sp, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onBlockTabComplete(BlockCommandSender bs, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender cs, Command cmd, String alias, String[] args) {
        return null;
    }
}

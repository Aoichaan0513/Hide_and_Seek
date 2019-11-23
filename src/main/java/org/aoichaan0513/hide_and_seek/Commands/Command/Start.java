package org.aoichaan0513.hide_and_seek.Commands.Command;

import org.aoichaan0513.hide_and_seek.API.GameManager;
import org.aoichaan0513.hide_and_seek.API.MainAPI;
import org.aoichaan0513.hide_and_seek.Commands.ICommand;
import org.aoichaan0513.hide_and_seek.Timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.List;

public class Start extends ICommand {

    public Start(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (!GameManager.isGame()) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("skip")) {
                    Timer.startTimer(1, 300);
                    return;
                } else {
                    try {
                        int i = Integer.parseInt(args[0]);
                        Bukkit.broadcastMessage(MainAPI.getPrefix(MainAPI.PrefixType.WARNING) + "カウントダウン開始");
                        Timer.startTimer(i + 1, 300);
                        return;
                    } catch (NumberFormatException ex) {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "数字を指定してください。");
                        return;
                    }
                }
            }
            Bukkit.broadcastMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "カウントダウン開始");
            Timer.startTimer(20, 300);
            return;
        }
        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "ゲームが実行されているため使用できません。");
        return;
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {
        return;
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {
        return;
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

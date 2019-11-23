package org.aoichaan0513.hide_and_seek;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.robingrether.idisguise.api.DisguiseAPI;
import org.aoichaan0513.hide_and_seek.API.MainAPI;
import org.aoichaan0513.hide_and_seek.API.Teams;
import org.aoichaan0513.hide_and_seek.Commands.Command.GEnd;
import org.aoichaan0513.hide_and_seek.Commands.Command.Start;
import org.aoichaan0513.hide_and_seek.Listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static JavaPlugin plugin;

    private static FileConfiguration mainConfig;

    // private static ProtocolManager protocolManager;
    private static DisguiseAPI disguise;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを起動しました。");

        // protocolManager = ProtocolLibrary.getProtocolManager();
        disguise = Bukkit.getServicesManager().getRegistration(DisguiseAPI.class).getProvider();

        saveDefaultConfig();
        mainConfig = getConfig();

        Teams.setScoreboard();

        getCommand("start").setExecutor(new Start("start"));
        getCommand("g_end").setExecutor(new GEnd("g_end"));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new onJoin(), this);
        pluginManager.registerEvents(new onQuit(), this);

        pluginManager.registerEvents(new onMove(), this);

        pluginManager.registerEvents(new onDamage(), this);

        pluginManager.registerEvents(new onInteract(), this);
    }

    @Override
    public void onDisable() {
        Teams.resetScoreboard();

        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを終了しました。");
    }

    public static JavaPlugin getInstance() {
        return plugin;
    }

    /*
    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }
    */

    public static DisguiseAPI getDisguise() {
        return disguise;
    }

    public static FileConfiguration getMainConfig() {
        return mainConfig;
    }

    public static FileConfiguration setMainConfig() {
        getInstance().saveConfig();
        mainConfig = getInstance().getConfig();
        return mainConfig;
    }
}

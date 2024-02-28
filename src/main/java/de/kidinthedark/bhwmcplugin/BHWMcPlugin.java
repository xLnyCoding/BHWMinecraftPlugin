package de.kidinthedark.bhwmcplugin;

import de.kidinthedark.bhwmcplugin.commands.LoreCommand;
import de.kidinthedark.bhwmcplugin.commands.RenameCommand;
import de.kidinthedark.bhwmcplugin.listeners.ChatListener;
import de.kidinthedark.bhwmcplugin.listeners.JoinListener;
import de.kidinthedark.bhwmcplugin.listeners.QuitListener;
import de.kidinthedark.bhwmcplugin.timers.RestartTimer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BHWMcPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        getCommand("rename").setExecutor(new RenameCommand());
        getCommand("lore").setExecutor(new LoreCommand());

        Bukkit.getScheduler().runTaskTimer(this, new RestartTimer(), 20, 20);
    }

    @Override
    public void onDisable() {
    }
}

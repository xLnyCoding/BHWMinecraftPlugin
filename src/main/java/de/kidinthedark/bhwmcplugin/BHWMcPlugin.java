package de.kidinthedark.bhwmcplugin;

import de.kidinthedark.bhwmcplugin.commands.*;
import de.kidinthedark.bhwmcplugin.listeners.ChatListener;
import de.kidinthedark.bhwmcplugin.listeners.InteractListener;
import de.kidinthedark.bhwmcplugin.listeners.JoinListener;
import de.kidinthedark.bhwmcplugin.listeners.QuitListener;
import de.kidinthedark.bhwmcplugin.recipes.LightRecipe;
import de.kidinthedark.bhwmcplugin.timers.ArmorStandCheckTimer;
import de.kidinthedark.bhwmcplugin.timers.RestartTimer;
import de.kidinthedark.bhwmcplugin.util.PluginDatabase;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.Objects;

public final class BHWMcPlugin extends JavaPlugin {

    public static BHWMcPlugin inst;
    public CoreProtectAPI coreProtect;
    public LightRecipe recipe;
    public PluginDatabase data;

    @Override
    public void onEnable() {

        inst = this;

        data = new PluginDatabase();

        recipe = new LightRecipe();

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getLogger().info("Plugin geladen!");

        Objects.requireNonNull(getCommand("rename")).setExecutor(new RenameCommand());
        Objects.requireNonNull(getCommand("lore")).setExecutor(new LoreCommand());
        Objects.requireNonNull(getCommand("wartung")).setExecutor(new WartungCommand());
        Objects.requireNonNull(getCommand("bc")).setExecutor(new BroadcastCommand());
        Objects.requireNonNull(getCommand("lookup")).setExecutor(new LookupCommand());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new BanCommand());
        Objects.requireNonNull(getCommand("tempban")).setExecutor(new TempbanCommand());
        Objects.requireNonNull(getCommand("ec")).setExecutor(new EnderchestCommand());
        //getCommand("signrename").setExecutor(new SignRenameCommand());

        Bukkit.getScheduler().runTaskTimer(this, new RestartTimer(), 20, 20);
        Bukkit.getScheduler().runTaskTimer(this, new ArmorStandCheckTimer(), 20*60, 20*60);

        coreProtect = loadCoreProtect();

        if (Bukkit.getRecipe(new NamespacedKey(BHWMcPlugin.inst, "light")) == null) Bukkit.addRecipe(recipe.getRecipe());
    }

    @Override
    public void onDisable() {

    }

    public CoreProtectAPI loadCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        CoreProtectAPI cp = ((CoreProtect) plugin).getAPI();

        Bukkit.getLogger().info("Lade CoreProtect Version " + cp.APIVersion());

        return cp;
    }

    public CoreProtectAPI getCoreProtect() {
        return coreProtect;
    }
}

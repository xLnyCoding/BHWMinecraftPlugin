package de.kidinthedark.bhwmcplugin.timers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class ArmorStandCheckTimer implements Runnable {
    @Override
    public void run() {
        for(World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if(entity.getType().equals(org.bukkit.entity.EntityType.ARMOR_STAND)) {
                    if(entity.getPassengers().isEmpty() && Component.text("chair").equals(entity.customName())) {
                        entity.remove();
                        Bukkit.getLogger().info("Removed chair");
                    }
                }
            }
        }
    }
}

package de.kidinthedark.bhwmcplugin.listeners;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import de.kidinthedark.bhwmcplugin.timers.RestartTimer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@Deprecated
public class BlockBreakListener implements Listener {

    public void onBreak(BlockBreakEvent e) {

        if(!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_AXE)) {
            return;
        }

        Block block = e.getBlock();

        if(!block.getType().name().toLowerCase().contains("log")) {
            return;
        }
        
    }



}

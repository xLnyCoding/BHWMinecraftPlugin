package de.kidinthedark.bhwmcplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§8[§c-§8]§7 " + e.getPlayer().getName());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.setLeaveMessage("§8[§c-§8]§7 " + e.getPlayer().getName());
    }

}

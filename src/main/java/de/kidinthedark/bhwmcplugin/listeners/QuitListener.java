package de.kidinthedark.bhwmcplugin.listeners;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import de.kidinthedark.bhwmcplugin.util.CachedPlayer;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIConnector;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class QuitListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        String uuid = e.getPlayer().getUniqueId().toString().replaceAll("-", "");

        CachedPlayer p = BHWMcPlugin.inst.data.getCachedPlayer(uuid);
        if(p != null) {
            p.setLastSeen(System.currentTimeMillis());
            BHWMcPlugin.inst.data.setCachedPlayer(p);
        }

        e.setQuitMessage("§8[§c-§8]§7 " + e.getPlayer().getName());
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onKick(PlayerKickEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString().replaceAll("-", "");

        CachedPlayer p = BHWMcPlugin.inst.data.getCachedPlayer(uuid);
        if(p != null) {
            p.setLastSeen(System.currentTimeMillis());
            BHWMcPlugin.inst.data.setCachedPlayer(p);
        }

        e.setLeaveMessage("§8[§c-§8]§7 " + e.getPlayer().getName());
    }

}

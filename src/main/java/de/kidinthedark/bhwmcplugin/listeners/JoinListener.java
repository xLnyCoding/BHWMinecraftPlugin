package de.kidinthedark.bhwmcplugin.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        e.setJoinMessage("§8[§a+§8]§7 " + e.getPlayer().getName());

        e.getPlayer().setPlayerListHeaderFooter("\n     §b§lBenHw Inoffiziell     \n", "\n§aPrivater Server\n");
    }

}

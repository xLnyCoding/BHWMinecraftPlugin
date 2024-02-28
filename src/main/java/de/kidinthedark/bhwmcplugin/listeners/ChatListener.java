package de.kidinthedark.bhwmcplugin.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @SuppressWarnings("deprecated")
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        message = message.replaceAll("%", "%%");
        message = ChatColor.translateAlternateColorCodes('&', message);

        e.setCancelled(true);

        if(message.startsWith("@")) {
            String playername = message.split(" ")[0].replaceFirst("@", "");
            Player target = Bukkit.getPlayer(playername);

            if(!target.isOnline()) {
                p.sendMessage("§cDer Spieler " + playername + " konnte nicht gefunden werden!");
                return;
            }

            message = message.replaceAll("@" + playername, "");

            if(message.isEmpty() || message.equals(" ")) {
                p.sendMessage("§cBitte lege eine Nachricht fest!");
            }

            message = message.replaceFirst(" ", "");
            p.sendMessage("§6Flüsternachricht an §a" + target.getName() + " §8» §7" + message);
            target.sendMessage("§6Flüsternachricht von §a" + p.getName() + " §8» §7" + message);
        } else {
            Bukkit.broadcastMessage("§6" + p.getName() + " §8» §7" + message);
        }
    }

}

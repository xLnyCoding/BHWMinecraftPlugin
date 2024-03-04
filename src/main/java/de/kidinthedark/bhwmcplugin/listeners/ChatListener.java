package de.kidinthedark.bhwmcplugin.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        String message = ((TextComponent) e.message()).content();
        message = ChatColor.translateAlternateColorCodes('&', message);

        e.setCancelled(true);

        if(message.startsWith("@")) {
            String playername = message.split(" ")[0].replaceFirst("@", "");
            OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(playername);

            if(!offlineTarget.isOnline()) {
                p.sendMessage("§cDer Spieler " + playername + " konnte nicht gefunden werden!");
                return;
            }
            Player target = Bukkit.getPlayer(playername);

            message = message.replaceAll("@" + playername, "");

            if(message.isEmpty() || message.equals(" ")) {
                p.sendMessage("§cBitte lege eine Nachricht fest!");
                return;
            }
            if(message.startsWith(" "))
                message = message.replaceFirst(" ", "");
            p.sendMessage("§6Flüsternachricht an §a" + target.getName() + " §8» §7" + message);
            target.sendMessage("§6Flüsternachricht von §a" + p.getName() + " §8» §7" + message);
        } else {
            Bukkit.broadcast(Component.text("§6" + p.getName() + " §8» §7" + message));
        }
    }

}

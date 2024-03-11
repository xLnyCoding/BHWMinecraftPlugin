package de.kidinthedark.bhwmcplugin.listeners;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import de.kidinthedark.bhwmcplugin.util.CachedPlayer;
import de.kidinthedark.bhwmcplugin.util.FileBuilder;
import de.kidinthedark.bhwmcplugin.util.PluginDatabase;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIConnector;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class JoinListener implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

        e.setJoinMessage("§8[§a+§8]§7 " + e.getPlayer().getName());

        e.getPlayer().discoverRecipe(BHWMcPlugin.inst.recipe.getKey());

        e.getPlayer().setPlayerListHeaderFooter("\n     §b§lBenHw Inoffiziell     \n", "\n§aPrivater Server\n");

    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) throws IOException {
        APIMessage uid = APIConnector.getPlayerId(e.getPlayer().getName());
        String uuid = uid.getValue("id");

        if(uid.getStatuscode() != 200) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cDie Mojang Server sind nicht erreichbar, deshalb kann dein Username nicht verifiziert werden\n§7Fehlercode: " + uid.getStatuscode()));
        }

        CachedPlayer cachedPlayer = new CachedPlayer(uuid, e.getPlayer().getName(), System.currentTimeMillis(), e.getAddress().toString());
        BHWMcPlugin.inst.data.setCachedPlayer(cachedPlayer);

        FileBuilder banInfo = BHWMcPlugin.inst.data.isPlayerBanned(cachedPlayer.getUuid());
        if(banInfo != null) {
            if(banInfo.getLong("duration") == -1) {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Component.text("§cDu wurdest gebannt!\n\n§eGrund: §7" + banInfo.getString("reason") + "\n§eEntbannungsdatum: §cPERMANENT" ));
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED, Component.text("§cDu wurdest gebannt!\n\n§eGrund: §7" + banInfo.getString("reason") + "\n§eEntbannungsdatum: §7" + new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(banInfo.getLong("expires"))));
            }
        }

        if(BHWMcPlugin.inst.data.isMaintenance() && !e.getPlayer().isOp()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Component.text("§cDer Server befindet sich in Wartungsarbeiten!"));
        }

    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        if(BHWMcPlugin.inst.data.isMaintenance()) {
            e.setMaxPlayers(0);
            e.motd(Component.text("§c§lDer Server wird gewartet"));
        }
    }

}

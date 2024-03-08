package de.kidinthedark.bhwmcplugin.commands;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import de.kidinthedark.bhwmcplugin.util.CachedPlayer;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIConnector;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class LookupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender.isOp()) {
            if(args.length < 1) {
                sender.sendMessage("§cBitte gebe einen Spielernamen an!");
                return false;
            }

            APIMessage uid = null;
            try {
                uid = APIConnector.getPlayerId(args[0]);
            } catch (IOException e) {
                    sender.sendMessage("§cDer Spieler §e" + args[0] + " §ckonnte nicht gefunden werden!");
                    return false;
            }

            if(uid.getStatuscode() == 404) {
                sender.sendMessage("§cDer Spieler §e" + args[0] + " konnte nicht gefunden werden!");
                return false;
            }

            if(uid.getStatuscode() != 200) {
                sender.sendMessage("§cFehler in der API Verbindung. Fehlercode: " + uid.getStatuscode());
                return false;
            }

            String uuid = uid.getValue("id").replaceAll("\"", "");

            if(!BHWMcPlugin.inst.data.isPlayerCached(uuid)) {
                sender.sendMessage("§cDer Spieler §e" + args[0] + " §cist nicht im Cache!");
                return false;
            }

            CachedPlayer p = BHWMcPlugin.inst.data.getCachedPlayer(uuid);

            sender.sendMessage("§7Daten über §b" + p.getName());
            sender.sendMessage("§7Name§8: §b" + p.getName());
            sender.sendMessage("§7UUID§8: §b" + p.getUuid());
            if(p.isOnline()) {
                sender.sendMessage("§7Status§8: §aONLINE");
            } else {
                sender.sendMessage("§7Status§8: §cOFFLINE");
                sender.sendMessage("§7Zuletzt online§8: §b" + new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(p.getLastSeen()));
            }
            sender.sendMessage("§7IP§8: §b" + p.getAddress());

        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }

        return false;
    }
}

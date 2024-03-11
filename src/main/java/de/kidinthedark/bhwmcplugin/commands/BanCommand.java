package de.kidinthedark.bhwmcplugin.commands;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import de.kidinthedark.bhwmcplugin.util.CachedPlayer;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIConnector;
import de.kidinthedark.bhwmcplugin.util.mcapi.APIMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender.isOp()) {
            if(args.length < 1) {
                sender.sendMessage("§cBitte gebe einen Spielernamen an!");
                return false;
            }
            if(args.length < 2) {
                sender.sendMessage("§cBitte gebe einen Grund an!");
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

            StringBuilder reason = new StringBuilder();
            reason.append(args[1]);

            for(int i = 2; i < args.length; ++i) {
                reason.append(" ").append(args[i]);
            }

            CachedPlayer target = BHWMcPlugin.inst.data.getCachedPlayer(uuid);

            BHWMcPlugin.inst.data.setPlayerBanned(target, reason.toString(), -1);

        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }

        return false;
    }
}

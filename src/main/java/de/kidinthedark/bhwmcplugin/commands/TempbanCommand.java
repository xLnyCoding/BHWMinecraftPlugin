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
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class TempbanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender.isOp()) {
            if(args.length < 1) {
                sender.sendMessage("§cBitte gebe einen Spielernamen an!");
                return false;
            }
            if(args.length < 2) {
                sender.sendMessage("§cBitte gebe eine Zeit an!");
                return false;
            }
            if(args.length < 3) {
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

            long time = Long.parseLong(args[1].substring(0,args[1].length()-1));
            String timeDiscriminator = args[1].replaceAll(String.valueOf(time), "");

            StringBuilder reason = new StringBuilder();
            reason.append(args[2]);

            for(int i = 3; i < args.length; ++i) {
                reason.append(" ").append(args[i]);
            }

            timeDiscriminator = timeDiscriminator.toLowerCase();
            switch (timeDiscriminator) {
                case "d":
                    time = time * 86400000;
                    break;
                case "m":
                    time = time * 86400000 * 30;
                    break;
                case "y":
                    time = time * 86400000 * 30 * 12;
                    break;
                default:
                    sender.sendMessage("§cBitte gib ein gültiges Zeitformat an!");
                    sender.sendMessage("§7Gültige Formate: d: Tage, m: Monate, y: Jahre");
                    return false;
            }

            CachedPlayer target = BHWMcPlugin.inst.data.getCachedPlayer(uuid);

            BHWMcPlugin.inst.data.setPlayerBanned(target, reason.toString(), time);

            String timeString = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(System.currentTimeMillis() + time);

            for(Player player : Bukkit.getOnlinePlayers()) {
                if (player.isOp()) player.sendMessage(Component.text("§c§lSystem §8»§a " + sender.getName() + "§7 hat §c" + target.getName() + "§7für §c" + reason + " §7gebannt! Entbannungsdatum: §c" + timeString));
            }
        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }

        return false;
    }
}

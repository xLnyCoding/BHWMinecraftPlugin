package de.kidinthedark.bhwmcplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class EnderchestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            return false;
        }

        if (!commandSender.isOp()) {
            return false;
        }

        if (args.length == 0) {
            return false;
        }

        Player p = Bukkit.getPlayer(args[0]);

        if (p == null) {
            return false;
        }

        ((Player) commandSender).openInventory(p.getEnderChest());
        return false;
    }
}

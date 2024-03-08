package de.kidinthedark.bhwmcplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {

    @Deprecated
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender.isOp()) {
            StringBuilder message = new StringBuilder();
            for(String string : strings) {
                message.append(" ").append(string);
            }
            String msg = ChatColor.translateAlternateColorCodes('&', message.toString());

            Bukkit.broadcast(Component.text("§c§lSystem §8»§7" + msg));
        } else {
            commandSender.sendMessage("§cDazu hast du keine Rechte!");
        }

        return false;
    }
}

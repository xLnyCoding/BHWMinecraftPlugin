package de.kidinthedark.bhwmcplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class RenameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length == 0) {
            sender.sendMessage("§cBitte gib einen Item-Namen an!");
            return false;
        }

        String name = args[0];

        for(int i = 1; i < args.length; i++) {
            name += " " + args[i];
        }

        name = name.replaceAll("&", "§");

        if(!name.startsWith("§")) {
            name = "§b§o" + name;
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack is = player.getItemInHand();
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(name);
            is.setItemMeta(im);

            player.setItemInHand(is);
        }

        return false;
    }
}

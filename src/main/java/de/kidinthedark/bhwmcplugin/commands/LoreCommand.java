package de.kidinthedark.bhwmcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoreCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length == 0) {
            sender.sendMessage("§cBitte gib eine Item-Lore an!");
            return false;
        }

        String name = args[0];

        for(int i = 1; i < args.length; i++) {
            name += " " + args[i];
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack is = player.getItemInHand();
            ItemMeta im = is.getItemMeta();
            List<String> namel = new ArrayList<>();
            namel.add(name);
            im.setLore(namel);
            is.setItemMeta(im);

            player.setItemInHand(is);
        }

        return false;
    }
}

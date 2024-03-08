package de.kidinthedark.bhwmcplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
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

        if (!(sender instanceof Player)) {
            sender.sendMessage("Du bist nicht berechtigt diesen Command auszuführen");
            return false;
        } else if (args.length == 0) {
            sender.sendMessage("§cBitte gib dem Item einem Namen");
            return false;
        } else {
            StringBuilder name = new StringBuilder();
            name.append(args[0]);

            for(int i = 1; i < args.length; ++i) {
                name.append(" ").append(args[i]);
            }

            String finalName = ChatColor.translateAlternateColorCodes('&', name.toString());
            Player p = (Player)sender;
            ItemStack is = p.getInventory().getItemInMainHand();
            ItemMeta im = is.getItemMeta();
            im.displayName(Component.text(finalName));
            is.setItemMeta(im);
            p.getInventory().setItemInMainHand(is);
            return false;
        }
    }
}

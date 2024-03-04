package de.kidinthedark.bhwmcplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SignRenameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein!");
        }

        int line = 0;

        switch (args[0]) {
            case "1":
            case "2":
            case "3":
            case "4":
        }

        return false;
    }
}

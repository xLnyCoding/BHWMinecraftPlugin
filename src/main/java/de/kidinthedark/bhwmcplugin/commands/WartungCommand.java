package de.kidinthedark.bhwmcplugin.commands;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WartungCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(sender.isOp()) {
            if(BHWMcPlugin.inst.data.isMaintenance()) {
                BHWMcPlugin.inst.data.setMaintenance(false);
                sender.sendMessage("§7Der Wartungsmodus wurde §adeaktiviert!");
            } else {
                BHWMcPlugin.inst.data.setMaintenance(true);
                sender.sendMessage("§7Der Wartungsmodus wurde §caktiviert!");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(!p.isOp())
                        p.kick(Component.text("§cDer Server befindet sich nun in Wartungsarbeiten!"));
                }
            }
        } else {
            sender.sendMessage("§cDazu hast du keine Rechte!");
        }

        return false;
    }

}

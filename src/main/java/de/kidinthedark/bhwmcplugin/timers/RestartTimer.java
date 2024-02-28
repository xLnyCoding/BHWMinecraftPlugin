package de.kidinthedark.bhwmcplugin.timers;

import com.google.common.util.concurrent.TimeLimiter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;

import java.sql.Time;
import java.text.SimpleDateFormat;

public class RestartTimer implements Runnable {

    private boolean announce30 = false;
    private boolean announce15 = false;
    private boolean announce10 = false;
    private boolean announce5 = false;
    private boolean announce1 = false;
    private boolean restart = false;
    private long min1time = 0;
    private long countdown = 10;

    @Override
    public void run() {
        String time = new SimpleDateFormat("HHmm").format(System.currentTimeMillis());
        switch (time) {
            case "0230":
                if(announce30) break;
                Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e30 Minuten §7neu gestartet.");
                announce30 = true;
                break;
            case "0245":
                if(announce15) break;
                Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e15 Minuten §7neu gestartet.");
                announce15 = true;
                break;
            case "0250":
                if(announce10) break;
                Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e10 Minuten §7neu gestartet.");
                announce10 = true;
                break;
            case "0255":
                if(announce5) break;
                Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e5 Minuten §7neu gestartet.");
                announce5 = true;
                break;
            case "0259":
                if(announce1) break;
                Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e1 Minute §7neu gestartet.");
                min1time = System.currentTimeMillis();
                announce1 = true;
                break;
            default:
                break;
        }

        if(announce1) {
            long timeleft = 60 - ((System.currentTimeMillis() - min1time) / 1000);
            if(timeleft<11) {
                restart = true;
            }
        }

        if(restart) {
                if (countdown > 1) {
                    Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e" + countdown + " Sekunden §7neu gestartet.");
                } else if (countdown == 1) {
                    Bukkit.broadcastMessage("§c§lServerrestart §8» §7Der Server wird in §e" + countdown + " Sekunde §7neu gestartet.");
                } else if (countdown < 1) {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.kick(Component.text("§cDer Server wird neu gestartet!"), PlayerKickEvent.Cause.RESTART_COMMAND);
                    }
                    Bukkit.spigot().restart();
                }
                countdown--;
        }



    }

}

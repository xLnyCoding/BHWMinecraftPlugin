package de.kidinthedark.bhwmcplugin.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            if (e.getClickedBlock().getBlockData() instanceof Stairs stairs) {
                BlockFace face = stairs.getFacing();
                if (stairs.getHalf().equals(Bisected.Half.TOP)) return;
                float yaw;
                switch (face) {
                    case NORTH:
                        yaw = 0.0f;
                        break;
                    case SOUTH:
                        yaw =  180.0f;
                        break;
                    case WEST:
                        yaw =  -90.0f;
                        break;
                    case EAST:
                        yaw =  90.0f; // Alternatively, return 270.0f
                        break;
                    default:
                        yaw =  0.0f; // Default fallback
                }
                Location location = e.getClickedBlock().getLocation();
                location.add(0.5, -1.5, 0.5);
                location.setYaw(yaw);
                Location ploc = location.clone();
                ploc.setYaw(yaw);
                e.getPlayer().teleport(ploc);
                ArmorStand armorStand = (ArmorStand) e.getPlayer().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
                armorStand.setInvulnerable(true);
                armorStand.setInvisible(true);
                armorStand.setGravity(false);
                armorStand.setPassenger(e.getPlayer());
                armorStand.customName(Component.text("chair"));
                e.setCancelled(true);
            }
        }
    }

}

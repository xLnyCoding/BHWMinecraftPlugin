package de.kidinthedark.bhwmcplugin.util;

import de.kidinthedark.bhwmcplugin.BHWMcPlugin;
import org.bukkit.Bukkit;
import java.util.List;

public class PluginDatabase {

    private final FileBuilder config;
    private final FileBuilder banlist;
    private final FileBuilder cachedPlayers;

    public PluginDatabase() {
        config = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "config.yml");
        banlist = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "banlist.yml");
        cachedPlayers = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath(), "cachedPlayers.yml");
    }

    public boolean isMaintenance() {
        return config.getBoolean("maintenance");
    }

    public void setMaintenance(boolean maint) {
        config.setValue("maintenance", maint);
        config.save();
    }

    public boolean isPlayerCached(String uuid) {
        return cachedPlayers.getSringList("players").contains(uuid);
    }

    public CachedPlayer getCachedPlayer(String uuid) {
        if(isPlayerCached(uuid)) {
            FileBuilder playerInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "playercache", uuid + ".yml");

            return new CachedPlayer(uuid, playerInfo.getString("name"), playerInfo.getLong("lastSeen"), Bukkit.getOfflinePlayer(uuid).isOnline(), playerInfo.getString("address"));
        }
        return null;
    }

    public void setCachedPlayer(CachedPlayer player) {
        List<String> players = banlist.getSringList("players");
        if(!players.contains(player.getUuid())) {
            players.add(player.getUuid());
            cachedPlayers.setValue("players", players);
            cachedPlayers.save();
        }
        FileBuilder playerInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "playercache", player.getUuid() + ".yml");

        if(!playerInfo.exist()) playerInfo.mkfile();

        playerInfo.setValue("uuid", player.getUuid());
        playerInfo.setValue("name", player.getName());
        playerInfo.setValue("lastSeen", player.getLastSeen());
        playerInfo.setValue("address", player.getAddress());
        playerInfo.save();
    }

    public boolean isPlayerBanned(String uuid) {
        if(banlist.getSringList("bans").contains(uuid)) {
            FileBuilder banInfo = new FileBuilder(BHWMcPlugin.inst.getDataFolder().getPath() + "bans", uuid + ".yml");

            if(banInfo.getLong("duration") == -1) return true;

            if(banInfo.getLong("expires") < System.currentTimeMillis()) {
                banInfo.delete();
                List<String> bans = banlist.getSringList("bans");
                bans.remove(uuid);
                banlist.setValue("banlist", bans);
                banInfo.save();
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    public void setPlayerBanned(CachedPlayer player, String reason, long duration) {

    }

}

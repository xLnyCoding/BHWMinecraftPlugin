package de.kidinthedark.bhwmcplugin.util;

public class CachedPlayer {

    private String uuid;
    private String name;
    private String address;
    private long lastSeen;
    private boolean online;

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public boolean isOnline() {
        return online;
    }

    public CachedPlayer(String uuid, String name, long lastSeen, boolean online, String ip) {

    }

}

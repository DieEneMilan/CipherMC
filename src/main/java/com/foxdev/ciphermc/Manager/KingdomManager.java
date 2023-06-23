package com.foxdev.ciphermc.Manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KingdomManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private Map<String, Kingdoms> kingdoms;
    public KingdomManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        loadKingdoms();
    }

    private void loadKingdoms() {
        kingdoms = new HashMap<>();
        for (String kingdomName : getKingdomNames()) {
            Kingdoms kingdom = new Kingdoms(kingdomName);
            kingdoms.put(kingdomName, kingdom);
        }
    }

    public Kingdoms getKingdom(String kingdomName) {
        return kingdoms.get(kingdomName);
    }

    public Kingdoms getPlayerKingdom(UUID playerUuid) {
        String kingdomName = config.getString("players." + playerUuid + ".kingdom");
        return kingdoms.get(kingdomName);
    }

    public List<String> getKingdomNames() {
        return config.getStringList("kingdoms");
    }

    public void createKingdom(String kingdomName) {
        Kingdoms kingdom = new Kingdoms(kingdomName);
        kingdoms.put(kingdomName, kingdom);
        updateConfig();
    }

    public void addPlayerToKingdom(String kingdomName, UUID playerUuid) {
        Kingdoms kingdom = kingdoms.get(kingdomName);
        kingdom.addMember(playerUuid);
        config.set("players." + playerUuid + ".kingdom", kingdomName);
        plugin.saveConfig();
    }

    public void removePlayerFromKingdom(UUID playerUuid) {
        Kingdoms kingdom = getPlayerKingdom(playerUuid);
        kingdom.removeMember(playerUuid);
        config.set("players." + playerUuid + ".kingdom", null);
        plugin.saveConfig();
    }

    private void updateConfig() {
        config.set("kingdoms", getKingdomNames());
        plugin.saveConfig();
    }
}

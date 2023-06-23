package com.foxdev.ciphermc.Claim;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaimManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private Map<Location, Claim> claims;

    public ClaimManager(JavaPlugin plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        loadClaims();
    }

    private void loadClaims() {
        claims = new HashMap<>();
        if (!config.isSet("claims")) return;
        for (String path : config.getConfigurationSection("claims").getKeys(false)) {
            String kingdomName = config.getString("claims." + path + ".kingdom");
            Location location = config.getLocation("claims." + path + ".location");
            Claim claim = new Claim(kingdomName, location);
            claims.put(location, claim);
        }
    }

    public Claim getClaim(Location location) {
        return claims.get(location);
    }

    public boolean claimTerritory(String kingdomName, Location location) {
        if (!isLocationClaimed(location)) {
            Claim claim = new Claim(kingdomName, location);
            claims.put(location, claim);
            updateConfig();
            return true;
        }
        return false;
    }

    public void unclaimTerritory(Location location) {
        claims.remove(location);
        updateConfig();
    }

    public boolean isLocationClaimed(Location location) {
        return claims.containsKey(location);
    }

    private void updateConfig() {
        List<Location> locations = new ArrayList<>(claims.keySet());
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            Claim claim = claims.get(location);
            config.set("claims.claim" + i + ".kingdom", claim.getKingdomName());
            config.set("claims.claim" + i + ".location", claim.getLocation());
        }
        plugin.saveConfig();
    }
}
package com.foxdev.ciphermc.Claim;

import org.bukkit.Location;

public class Claim {
    private String kingdomName;
    private Location location;

    public Claim(String kingdomName, Location location) {
        this.kingdomName = kingdomName;
        this.location = location;
    }

    public String getKingdomName() {
        return kingdomName;
    }

    public Location getLocation() {
        return location;
    }
}
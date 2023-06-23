package com.foxdev.ciphermc.Listener;

import com.foxdev.ciphermc.Manager.KingdomManager;
import com.foxdev.ciphermc.Manager.KingdomTablist;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class kingdomJoinListener implements Listener {
    private KingdomManager kingdomManager;
    private KingdomTablist kingdomTablist;

    public kingdomJoinListener(KingdomManager kingdomManager, KingdomTablist kingdomTablist) {
        this.kingdomManager = kingdomManager;
        this.kingdomTablist = kingdomTablist;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        kingdomTablist.updateTablist(event.getPlayer());
    }

    @EventHandler
    public void onJoinMessage(PlayerJoinEvent event){
        event.setJoinMessage(event.getPlayer().getDisplayName() + " is gejoint");
    }
}

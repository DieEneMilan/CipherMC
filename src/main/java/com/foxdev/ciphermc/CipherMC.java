package com.foxdev.ciphermc;

import com.foxdev.ciphermc.Claim.ClaimManager;
import com.foxdev.ciphermc.Commands.KingdomCommand;
import com.foxdev.ciphermc.Listener.kingdomJoinListener;
import com.foxdev.ciphermc.Manager.KingdomManager;
import com.foxdev.ciphermc.Manager.KingdomTablist;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class CipherMC extends JavaPlugin {


    private FileConfiguration configuration;
    private KingdomManager kingdomManager;
    private ClaimManager claimManager;
    private KingdomTablist kingdomTablist;


    @Override
    public void onEnable() {
        kingdomManager = new KingdomManager(this);
        claimManager = new ClaimManager(this);
        kingdomTablist = new KingdomTablist(kingdomManager); // pass the kingdomManager object here
        kingdomJoinListener kingdomJoinListener = new kingdomJoinListener(kingdomManager, kingdomTablist);
        getServer().getPluginManager().registerEvents(kingdomJoinListener, this);
        getCommand("kingdom").setExecutor(new KingdomCommand(kingdomManager, claimManager, kingdomTablist));
    }

    @Override
    public void onDisable() {
    }
}

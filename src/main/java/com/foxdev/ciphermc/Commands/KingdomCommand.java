package com.foxdev.ciphermc.Commands;

import com.foxdev.ciphermc.Claim.Claim;
import com.foxdev.ciphermc.Claim.ClaimManager;
import com.foxdev.ciphermc.Manager.KingdomManager;
import com.foxdev.ciphermc.Manager.KingdomTablist;
import com.foxdev.ciphermc.Manager.Kingdoms;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class KingdomCommand implements CommandExecutor {

    private KingdomManager kingdomManager;
    private ClaimManager claimManager;

    public KingdomCommand(KingdomManager kingdomManager, ClaimManager claimManager, KingdomTablist kingdomTablist){
        this.kingdomManager = kingdomManager;
        this.claimManager = claimManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("ONLY PLAYERS!");
            return true;
        }

        Player player = (Player)  commandSender;
        UUID playerUuid = player.getUniqueId();


        String subCommand = args[0].toLowerCase();
        switch (subCommand){
            case "create":
                if(args.length >= 2){
                    String kingdomName = args[1];
                    kingdomManager.createKingdom(kingdomName);
                    kingdomManager.addPlayerToKingdom(kingdomName, playerUuid);
                    player.sendMessage("Kingdom " + kingdomName + "gemaakt en toegetreden!");
                } else {
                    player.sendMessage("Gebruik /kingdom create <name>");
                }
                break;
            case "join":
                if(args.length >= 2){
                    String kingdomName = args[1];
                    if(kingdomManager.getKingdomNames().contains(kingdomName)){
                        kingdomManager.addPlayerToKingdom(kingdomName, playerUuid);
                        player.sendMessage("Je bent nu lid van het kingdom " + kingdomName + "!");
                    } else {
                        player.sendMessage("Kingdom " + kingdomName + " bestaat niet!");
                    }
                } else {
                    player.sendMessage("Gebruik /kingdom join <kingdom>");
                }
                break;
            case "leave":
                Kingdoms currentKingdom = kingdomManager.getPlayerKingdom(playerUuid);
                if (currentKingdom != null) {
                    kingdomManager.removePlayerFromKingdom(playerUuid);
                    player.sendMessage(ChatColor.GREEN + "Je hebt het koninkrijk " + currentKingdom.getName() + " verlaten!");
                } else {
                    player.sendMessage(ChatColor.RED + "Je zit momenteel niet in een koninkrijk!");
                }
                break;

            case "claim":
                Kingdoms playerKingdom = kingdomManager.getPlayerKingdom(playerUuid);
                if (playerKingdom != null) {
                    if (claimManager.claimTerritory(playerKingdom.getName(), player.getLocation())) {
                        player.sendMessage(ChatColor.GREEN + "Gebied geclaimd voor koninkrijk " + playerKingdom.getName() + "!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Dit gebied is al geclaimd!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Je bent geen lid van een koninkrijk!");
                }
                break;
            case "unclaim":
                Claim claim = claimManager.getClaim(player.getLocation());
                if (claim != null && claim.getKingdomName().equals(kingdomManager.getPlayerKingdom(playerUuid).getName())) {
                    claimManager.unclaimTerritory(player.getLocation());
                    player.sendMessage(ChatColor.GREEN + "Gebied opgeëist!");
                } else {
                    player.sendMessage(ChatColor.RED + "Je kunt dit gebied niet opvorderen!");
                }
                break;
            default:
                player.sendMessage(ChatColor.RED + "Ongeldig subcommando. Gebruik /kingdom create|join|leave");
        }
        return true;
    }
}

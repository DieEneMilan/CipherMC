package com.foxdev.ciphermc.Manager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class KingdomTablist {

    private KingdomManager kingdomManager;

    public KingdomTablist(KingdomManager kingdomManager) {
        this.kingdomManager = kingdomManager;
    }

    public void updateTablist(Player player) {
        Kingdoms playerKingdom = kingdomManager.getPlayerKingdom(player.getUniqueId());
        String kingdomName = playerKingdom != null ? playerKingdom.getName() : "Geen";

        String teamName = kingdomName.length() > 16 ? kingdomName.substring(0, 16) : kingdomName;
        Team kingdomTeam = player.getScoreboard().getTeam(teamName);

        if (kingdomTeam == null) {
            kingdomTeam = player.getScoreboard().registerNewTeam(teamName);
        }

        kingdomTeam.setPrefix(ChatColor.YELLOW + kingdomName + " " + ChatColor.RESET);

        if (!kingdomTeam.hasPlayer(player)) {
            for (Team team : player.getScoreboard().getTeams()) {
                if (team.hasPlayer(player)) {
                    team.removePlayer(player);
                }
            }
            kingdomTeam.addPlayer(player);
        }
    }
}
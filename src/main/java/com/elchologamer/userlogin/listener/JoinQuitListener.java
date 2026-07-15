package com.elchologamer.userlogin.listener;

import com.elchologamer.userlogin.ULPlayer;
import com.elchologamer.userlogin.UserLogin;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!UserLogin.getPlugin().getConfig().getBoolean("vanillaJoinMessages", !UserLogin.getPlugin().getConfig().getBoolean("disableVanillaJoinMessages", true))) {
            event.setJoinMessage(null);
        }
        ULPlayer.get(event.getPlayer()).onJoin(false);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        // Check if player already online
        for(Player player : Bukkit.getOnlinePlayers()) {
            ULPlayer ulPlayer = ULPlayer.get(player);
            if(player.getName().equalsIgnoreCase(event.getName())) {
                if(ulPlayer.isLoggedIn()) {
                    event.setLoginResult(Result.KICK_OTHER);
                    event.setKickMessage(ChatColor.RED + "This player is already online!");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        ULPlayer.get(event.getPlayer()).onQuit();
    }
}
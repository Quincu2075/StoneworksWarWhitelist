package me.quincu2075.warwhitelist.listener;

import me.quincu2075.warwhitelist.SWW;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncLoginListener implements Listener {

    private SWW sww;

    public AsyncLoginListener(SWW sww){
        this.sww = sww;
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        UUID uuid = event.getUniqueId();
        if (!this.sww.getWhitelistManager().isWhitelistEnabled()) return;
        if (this.sww.getWhitelistManager().isWhitelisted(uuid)) return;
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', sww.getConfig().getString("kick-message")));
    }

}

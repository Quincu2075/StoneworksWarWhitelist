package me.quincu2075.warwhitelist.broadcast;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class LocalBroadcaster implements Broadcaster{
    @Override
    public void broadcast(String msg, CommandSender sender) {
        Bukkit.broadcastMessage(msg);
    }
}

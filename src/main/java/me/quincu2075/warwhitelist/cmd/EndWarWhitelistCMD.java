package me.quincu2075.warwhitelist.cmd;

import me.quincu2075.warwhitelist.SWW;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndWarWhitelistCMD implements CommandExecutor {

    private SWW sww;

    public EndWarWhitelistCMD(SWW sww){
        this.sww = sww;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!sender.hasPermission("warwhitelist.end")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
            return true;
        }

        if (!this.sww.getWhitelistManager().isWhitelistEnabled()){
            sender.sendMessage(ChatColor.RED + "The war whitelist is not currently enabled!");
            return true;
        }

        this.sww.getWhitelistManager().stop();
        sender.sendMessage(ChatColor.GREEN + "The war whitelist has been turned off.");

        return true;
    }
}

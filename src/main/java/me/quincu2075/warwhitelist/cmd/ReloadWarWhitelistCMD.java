package me.quincu2075.warwhitelist.cmd;

import me.quincu2075.warwhitelist.SWW;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadWarWhitelistCMD implements CommandExecutor {

    private SWW sww;

    public ReloadWarWhitelistCMD(SWW sww){
        this.sww = sww;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("warwhitelist.reload")){
            commandSender.sendMessage(ChatColor.RED + "You do not have permission!");
            return true;
        }
        sww.saveDefaultConfig();
        sww.reloadConfig();
        commandSender.sendMessage(ChatColor.GREEN + "Config has been reloaded!!");
        return true;
    }
}

package me.quincu2075.warwhitelist.cmd;

import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.memberholder.MemberHolder;
import me.angeschossen.lands.api.war.War;
import me.quincu2075.warwhitelist.SWW;
import me.quincu2075.warwhitelist.whitelist.WhitelistManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartWarWhitelistCMD implements CommandExecutor {

    private SWW sww;

    public StartWarWhitelistCMD(SWW sww){
        this.sww = sww;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!commandSender.hasPermission("warwhitelist.start")){
            commandSender.sendMessage(ChatColor.RED + "You do not have permission!");
            return true;
        }

        if (args.length == 0){
            commandSender.sendMessage(ChatColor.RED + "Improper usage! /startwarwhitelist <Land> [AnotherLand]");
            return true;
        }

        MemberHolder[] war = null;

        if (args.length == 1) {
            String name = args[0];
            Land land = SWW.li.getLandByName(name);

            if (land == null) {
                commandSender.sendMessage(ChatColor.RED + "Land not found! try again!");
                return true;
            }

            War warRn = land.getWar();

            if (warRn == null) {
                commandSender.sendMessage(ChatColor.RED + "War not found for that land! Has it started yet? Try entering an additional land arg!");
                return true;
            }

            war = new MemberHolder[]{warRn.getAttacker(), warRn.getDefender()};

        }else {
            String name1 = args[0];
            String name2 = args[1];

            Land land1 = SWW.li.getLandByName(name1);
            Land land2 = SWW.li.getLandByName(name2);

            if (land1 == null) {
                commandSender.sendMessage(ChatColor.RED + "First Land was not found! try again!");
                return true;
            }

            if (land2 == null) {
                commandSender.sendMessage(ChatColor.RED + "Second Land was not found! try again!");
                return true;
            }

            war = new MemberHolder[]{land1, land2};
        }

        WhitelistManager wm = sww.getWhitelistManager();
        wm.enable(sww.getConfig().getInt("grace-before-kick"), war);

        commandSender.sendMessage(ChatColor.GREEN + "Enabled war whitelist!");

        return true;
    }
}

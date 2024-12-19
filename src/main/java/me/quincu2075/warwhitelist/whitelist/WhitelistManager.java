package me.quincu2075.warwhitelist.whitelist;

import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.memberholder.MemberHolder;
import me.angeschossen.lands.api.war.War;
import me.quincu2075.warwhitelist.SWW;
import me.quincu2075.warwhitelist.listener.WarStartListener;
import me.quincu2075.warwhitelist.score.PeriodicScoreTask;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class WhitelistManager {

    public static String WHITELIST_BYPASS_PERMISSION = "warwhitelist.bypass";

    protected boolean whitelistEnabled;
    private MemberHolder[] activeWar;
    private final SWW plugin;
    private WhitelistGraceSchedule grace;
    private final LuckPerms luckPerms;

    public WhitelistManager(SWW plugin){
        this.whitelistEnabled = false;
        this.plugin = plugin;
        this.luckPerms = LuckPermsProvider.get();

        String scoreMsg = plugin.getConfig().getString("score-format");
        if (scoreMsg != null && !scoreMsg.equalsIgnoreCase("")) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                @Override
                public void run() {
                    if (activeWar == null) return;
                    if (activeWar.length != 2) return;
                    if (activeWar[0].getWar() == null) return;

                    War war1 = activeWar[0].getWar();
                    if (WarStartListener.periodicScoreTask != null) return;
                    int interval = plugin.getConfig().getInt("score-interval");
                    WarStartListener.periodicScoreTask = new PeriodicScoreTask(war1, interval, plugin);
                }
            }, 30L, 30L);
        }
    }

    public boolean enable(int secondsUntilWhitelist, MemberHolder[] war){
        if (war == null){
            return false;
        }
        grace = new WhitelistGraceSchedule(secondsUntilWhitelist, plugin, this);
        activeWar = war;

        int max = plugin.getConfig().getInt("war-max");
        if (max != -1){
            try {
                Bukkit.setMaxPlayers(max);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        this.whitelistEnabled = true;
        plugin.getLogger().info("War Whitelist has been enabled!");

        return true;
    }

    public void stop(){
        if (this.grace != null) {
            this.grace.stop();
        }
        whitelistEnabled = false;
        activeWar = null;

        int max = plugin.getConfig().getInt("war-max");
        if (max != -1){
            try {
                Bukkit.setMaxPlayers(SWW.SERVER_CAP);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public boolean isWhitelistEnabled(){
        return this.whitelistEnabled;
    }

    public boolean isWhitelisted(UUID player){

        if (Bukkit.getOfflinePlayer(player).isWhitelisted()) return true;

        if (activeWar == null) return true;

        if (activeWar.length >= 2) {
            if (activeWar[0].getTrustedPlayers().contains(player)) return true;
            if (activeWar[1].getTrustedPlayers().contains(player)) return true;
        }

        User user = this.luckPerms.getUserManager().getUser(player);
        Node node = PermissionNode.builder(WHITELIST_BYPASS_PERMISSION).build();

        return user.getNodes(NodeType.PERMISSION).contains(node);

    }

}

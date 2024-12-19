package me.quincu2075.warwhitelist.listener;

import me.angeschossen.lands.api.events.war.WarEndEvent;
import me.angeschossen.lands.api.events.war.WarStartEvent;
import me.angeschossen.lands.api.war.enums.WarTeam;
import me.quincu2075.warwhitelist.SWW;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarEndListener implements Listener {

    private SWW sww;

    public WarEndListener(SWW sww){
        this.sww = sww;
    }

    @EventHandler
    public void onEnd(WarEndEvent event){
        if (this.sww.getWhitelistManager().isWhitelistEnabled()) {
            this.sww.getWhitelistManager().stop();
        }

        if (this.sww.getConfig().getBoolean("end-keepinv")){
            for (String name : this.sww.getConfig().getStringList("end-keepinv-worlds")){
                try {
                    World w = Bukkit.getWorld(name);
                    w.setGameRule(GameRule.KEEP_INVENTORY, true);
                }catch (NullPointerException npe){
                    //Wrong worldname or doesn't exist!
                    npe.printStackTrace();
                }
            }
        }

        String endMsg = this.sww.getConfig().getString("end-format");
        if (endMsg == null) return;
        if (endMsg.equalsIgnoreCase("")) return;

        float dPoints = event.getWar().getStats(WarTeam.DEFENDER).getPointsTotal();
        float aPoints = event.getWar().getStats(WarTeam.ATTACKER).getPointsTotal();
        String dClaim = event.getWar().getDefender().getName();
        String aClaim = event.getWar().getAttacker().getName();
        endMsg = endMsg.replaceAll("%defender-claim%", dClaim);
        endMsg = endMsg.replaceAll("%attacker-claim%", aClaim);
        endMsg = endMsg.replaceAll("%defender-score%", String.valueOf(dPoints));
        endMsg = endMsg.replaceAll("%attacker-score%", String.valueOf(aPoints));
        endMsg = ChatColor.translateAlternateColorCodes('&', endMsg);

        Player player = null;
        if (Bukkit.getOnlinePlayers().iterator().hasNext()) {
            player = Bukkit.getOnlinePlayers().iterator().next();
            sww.getLogger().warning(player.getName());
        }

        int i = 0;
        while (i < sww.getConfig().getInt("end-times")){
            sww.getBroadcaster().broadcast(endMsg, player);
            i++;
        }

    }

}

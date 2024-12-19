package me.quincu2075.warwhitelist.score;

import me.angeschossen.lands.api.land.Land;
import me.angeschossen.lands.api.memberholder.MemberHolder;
import me.angeschossen.lands.api.war.War;
import me.angeschossen.lands.api.war.enums.WarTeam;
import me.quincu2075.warwhitelist.SWW;
import me.quincu2075.warwhitelist.listener.WarStartListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PeriodicScoreTask {

    private MemberHolder land;
    private int intervalSeconds;
    private int task;

    public PeriodicScoreTask(War war, int intervalSeconds, SWW sww){
        this.land = war.getAttacker();
        this.intervalSeconds = intervalSeconds;
        this.task = Bukkit.getScheduler().scheduleSyncRepeatingTask(sww, new Runnable() {
            @Override
            public void run() {
                Land land1 = SWW.li.getLandByULID(land.getULID());
                if (land1 == null || land1.getWar() == null) {
                    Bukkit.getScheduler().cancelTask(task);
                    WarStartListener.periodicScoreTask = null;
                    return;
                }
                War war1 = land1.getWar(); //I've had to keep getting the object to avoid issues with the score not updating.
                long timeLeftSeconds = war1.getTimeLeft();
                int seconds = (int) timeLeftSeconds % 60;
                int minutes = (int) timeLeftSeconds / 60;

                String scoreMsg = sww.getConfig().getString("score-format");
                if (scoreMsg == null) return;
                if (scoreMsg.equalsIgnoreCase("")) return;

                float dPoints = war1.getStats(WarTeam.DEFENDER).getPointsTotal();
                float aPoints = war1.getStats(WarTeam.ATTACKER).getPointsTotal();
                String dClaim = war1.getDefender().getName();
                String aClaim = war1.getAttacker().getName();
                scoreMsg = scoreMsg.replaceAll("%defender-claim%", dClaim);
                scoreMsg = scoreMsg.replaceAll("%attacker-claim%", aClaim);
                scoreMsg = scoreMsg.replaceAll("%defender-score%", String.valueOf(dPoints));
                scoreMsg = scoreMsg.replaceAll("%attacker-score%", String.valueOf(aPoints));
                scoreMsg = scoreMsg.replaceAll("%seconds%", String.valueOf(seconds));
                scoreMsg = scoreMsg.replaceAll("%minutes%", String.valueOf(minutes));

                scoreMsg = ChatColor.translateAlternateColorCodes('&', scoreMsg);

                Player player = null;
                if (Bukkit.getOnlinePlayers().iterator().hasNext()) {
                    player = Bukkit.getOnlinePlayers().iterator().next();
                    sww.getLogger().warning(player.getName());
                }

                sww.getBroadcaster().broadcast(scoreMsg, player);
            }
        }, intervalSeconds * 20L, intervalSeconds * 20L);
    }

}

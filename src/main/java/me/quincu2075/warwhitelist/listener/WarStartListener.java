package me.quincu2075.warwhitelist.listener;

import me.angeschossen.lands.api.events.war.WarStartEvent;
import me.angeschossen.lands.api.memberholder.MemberHolder;
import me.angeschossen.lands.api.war.War;
import me.quincu2075.warwhitelist.SWW;
import me.quincu2075.warwhitelist.score.PeriodicScoreTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class WarStartListener implements Listener {

    private SWW sww;
    public static PeriodicScoreTask periodicScoreTask;

    public WarStartListener(SWW sww){
        this.sww = sww;
        periodicScoreTask = null;
    }

    @EventHandler
    public void onStart(WarStartEvent event){
        War war = event.getWar();
        //MemberHolder[] memberHolders = new MemberHolder[]{war.getAttacker(), war.getDefender()};
        //sww.getWhitelistManager().enable(sww.getConfig().getInt("grace-before-kick"), memberHolders);

        String scoreMsg = sww.getConfig().getString("score-format");
        if (scoreMsg == null) return;
        if (scoreMsg.equalsIgnoreCase("")) return;
        sww.getLogger().warning("War Started!");
        if (periodicScoreTask != null) return;
        sww.getLogger().warning("Score task started!");
        periodicScoreTask = new PeriodicScoreTask(war, sww.getConfig().getInt("score-interval"), sww);
    }

}

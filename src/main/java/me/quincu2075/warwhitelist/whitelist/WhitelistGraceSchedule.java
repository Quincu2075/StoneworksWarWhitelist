package me.quincu2075.warwhitelist.whitelist;

import me.quincu2075.warwhitelist.SWW;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class WhitelistGraceSchedule {

    private int[] taskIds;

    public WhitelistGraceSchedule(int secondsUntilWhitelist, JavaPlugin plugin, WhitelistManager whitelistManager){

        plugin.getLogger().info("Seconds: " + secondsUntilWhitelist);

        int s1 = secondsUntilWhitelist;
        int s2 = (secondsUntilWhitelist / 3) * 2;
        int s3 = secondsUntilWhitelist / 3;

        int[] times = new int[]{s1, s2, s3};

        int[] taskIds = new int[4];
        int i = 0;
        for (int time : times){
            long ticks = time * 20L;
            int task  = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                @Override
                public void run() {
                    String message = SWW.conf.getString("grace-message");
                    message = message.replaceAll("%seconds%", String.valueOf(time));
                    for (Player player : Bukkit.getOnlinePlayers()){
                        if (!whitelistManager.isWhitelisted(player.getUniqueId())){
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
                        }
                    }
                }
            }, (long) i * s3 * 20);
            plugin.getLogger().info("Ticks: " + ticks);
            taskIds[i] = task;
            i++;
        }
        taskIds[3] = Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (!whitelistManager.isWhitelisted(player.getUniqueId())){
                        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("kick-message")));
                    }
                }
                whitelistManager.whitelistEnabled = true;
            }
        }, secondsUntilWhitelist * 20L).getTaskId();
        this.taskIds = taskIds;
    }

    public void stop(){
        for (int task : taskIds){
            try {
                Bukkit.getScheduler().cancelTask(task);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}

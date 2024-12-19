package me.quincu2075.warwhitelist;

import me.angeschossen.lands.api.LandsIntegration;
import me.quincu2075.warwhitelist.broadcast.Broadcaster;
import me.quincu2075.warwhitelist.broadcast.GlobalBroadcaster;
import me.quincu2075.warwhitelist.broadcast.LocalBroadcaster;
import me.quincu2075.warwhitelist.cmd.EndWarWhitelistCMD;
import me.quincu2075.warwhitelist.cmd.ReloadWarWhitelistCMD;
import me.quincu2075.warwhitelist.cmd.StartWarWhitelistCMD;
import me.quincu2075.warwhitelist.listener.AsyncLoginListener;
import me.quincu2075.warwhitelist.listener.WarEndListener;
import me.quincu2075.warwhitelist.listener.WarStartListener;
import me.quincu2075.warwhitelist.whitelist.WhitelistManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class SWW extends JavaPlugin {

    public static LandsIntegration li;
    public static FileConfiguration conf;
    private WhitelistManager whitelistManager;
    private Broadcaster broadcaster;
    public static final int SERVER_CAP = Bukkit.getMaxPlayers();

    @Override
    public void onEnable() {
        // Plugin startup logic
        li = LandsIntegration.of(this);
        saveDefaultConfig();
        conf = getConfig();

        if (!conf.getBoolean("broadcast-global")){
            broadcaster = new LocalBroadcaster();
        }else {
            //LocalBroadcast used as fallback for when redischat fails.
            broadcaster = new GlobalBroadcaster(new LocalBroadcaster());
        }

        this.whitelistManager = new WhitelistManager(this);

        Listener[] listeners = new Listener[]{
                new AsyncLoginListener(this),
                new WarEndListener(this),
                new WarStartListener(this)
        };

        for (Listener l : listeners){
            Bukkit.getPluginManager().registerEvents(l, this);
        }

        getCommand("endwarwhitelist").setExecutor(new EndWarWhitelistCMD(this));
        getCommand("startwarwhitelist").setExecutor(new StartWarWhitelistCMD(this));
        getCommand("reloadwarwhitelist").setExecutor(new ReloadWarWhitelistCMD(this));

        getLogger().info("StoneworksWarWhitelist, by DevilNamedQuincu, has enabled!");
    }

    public Broadcaster getBroadcaster() {
        return broadcaster;
    }

    public WhitelistManager getWhitelistManager() {
        return whitelistManager;
    }

    @Override
    public void onDisable() {
        getLogger().info("StoneworksWarWhitelist, by DevilNamedQuincu, has disabled!");
    }
}

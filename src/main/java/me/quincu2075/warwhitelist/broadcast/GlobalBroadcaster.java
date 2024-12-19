package me.quincu2075.warwhitelist.broadcast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;

public class GlobalBroadcaster implements Broadcaster{

    private Broadcaster lb;

    public GlobalBroadcaster(Broadcaster fallback){
        lb = fallback;
    }

    @Override
    public void broadcast(String msg, CommandSender sender) {
        if (sender == null){
            lb.broadcast(msg, null);
            return;
        }
        try {

            Class<?> redisChatApi = Class.forName("dev.unnm3d.redischat.api.RedisChatAPI");
            Method publicChannelMethod = redisChatApi.getMethod("getPublicChannel", CommandSender.class);
            Object publicChannel = publicChannelMethod.invoke(redisChatApi, sender);

            Method method = null;

            for (Method m : redisChatApi.getMethods()){
                if (m.getName().contains("broadcastMessage")) method = m;
            }

            method.invoke(redisChatApi, publicChannel, msg);

        }catch (Exception e){
            lb.broadcast(msg,sender);
        }
    }
}

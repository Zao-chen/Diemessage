package diemessage.diemessage.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Mainevent implements Listener {
    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event)
    {
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        if(config.getConfig().getString("intercept").equals("on"))
        {
            event.getEntity().getPlayer().sendMessage("Die Message :\n"+event.getDeathMessage());
            event.getEntity().getPlayer().sendMessage("%killer:"+event.getEntity().getKiller().getName());
        }
        int i=0;
        List<String> list = config.getConfig().getStringList("message");
        for(String str : list)
        {
            String bi1 = event.getDeathMessage();
            String bi2 = str.replace("%player%",event.getEntity().getPlayer().getName()+"");
            if(event.getEntity().getKiller()!=null) {
                bi2 = bi2.replace("%killer%", event.getEntity().getKiller().getName() + "");
            }
            if (bi1.
                    equals(bi2))
            {
                if(list.get(i+2).contains("or:"))
                {
                    long a = System.currentTimeMillis();
                    int aa = (int) a;
                    aa = Math.abs(aa);
                    if(aa%2==0)
                    {
                        String set = list.get(i + 1);
                        set =set.replace("%player%", event.getEntity().getPlayer().getName() + "");
                        if(event.getEntity().getKiller()!=null) set =set.replace("%killer%",event.getEntity().getKiller().getName()+"");
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                        {
                            event.setDeathMessage(set);
                            return;
                        }
                        else
                        {
                            set = PlaceholderAPI.setPlaceholders(event.getEntity().getPlayer(),set);
                            event.setDeathMessage(set);
                            return;
                        }
                    }
                    else
                    {
                        String set = list.get(i + 2);
                        set = set.replace("or:","");
                        set =set.replace("%player%", event.getEntity().getPlayer().getName() + "");
                        if(event.getEntity().getKiller()!=null) set = set.replace("%killer%",event.getEntity().getKiller().getName()+"");
                        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                        {
                            event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                            return;
                        }
                        else
                        {
                            set = PlaceholderAPI.setPlaceholders(event.getEntity().getPlayer(),set);
                            event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                            //if(event.getEntity().getKiller()!=null) set.replace("%killer%",event.getEntity().getKiller().getName()+"");
                            return;
                        }
                    }
                }
                else {
                    String set = list.get(i + 1);
                    if(event.getEntity().getKiller()!=null) set = set.replace("%killer%",event.getEntity().getKiller().getName()+"");
                    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                    {
                        event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                        return;
                    }
                    else
                    {
                        set = PlaceholderAPI.setPlaceholders(event.getEntity().getPlayer(),set);
                        event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                        return;
                    }
                }
            }
            i++;
        }
    }
}

package diemessage.diemessage.events;

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
        int i=0;
        List<String> list = config.getConfig().getStringList("message");
        for(String str : list)
        {
            String bi1 = event.getDeathMessage();
            String bi2 = str.replace("%player%",event.getEntity().getPlayer().getName()+"");
            if (bi1.equals(bi2))
            {
                if(list.get(i+2).contains("or:"))
                {
                    long a = System.currentTimeMillis();
                    int aa = (int) a;
                    aa = Math.abs(aa);
                    if(aa%2==0)
                    {
                        String set = list.get(i + 1);
                        event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                        return;
                    }
                    else
                    {
                        String set = list.get(i + 2);
                        set = set.replace("or:","");
                        event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                        return;
                    }
                }
                else {
                    String set = list.get(i + 1);
                    event.setDeathMessage(set.replace("%player%", event.getEntity().getPlayer().getName() + ""));
                    return;
                }
            }
            i++;
        }
    }
}

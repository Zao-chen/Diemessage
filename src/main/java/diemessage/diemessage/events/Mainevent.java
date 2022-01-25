package diemessage.diemessage.events;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/*
 * 这个是整个插件的核心监听器
*/

public class Mainevent implements Listener {
    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event)
    {
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        File aconfig = new File(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class).getDataFolder(),"settings.yml");
        FileConfiguration cconfig = YamlConfiguration.loadConfiguration(aconfig);
        List<String> list = config.getConfig().getStringList("message");

        if(Objects.equals(cconfig.getString("intercept"), "true")) //如果开启了拦截
        {
            Objects.requireNonNull(event.getEntity().getPlayer()).sendMessage(ChatColor.YELLOW+"+----------+");
            event.getEntity().getPlayer().sendMessage("Die Message :\n"+event.getDeathMessage());
            if(event.getEntity().getKiller()!=null) event.getEntity().getPlayer().sendMessage("{killer}: "+event.getEntity().getKiller().getName());
            event.getEntity().getPlayer().sendMessage("{player}: "+event.getEntity().getPlayer().getName());
            try
            {
                event.getEntity().getPlayer().sendMessage("{arm}: "+ Objects.requireNonNull(Objects.requireNonNull(event.getEntity().getKiller().getEquipment()).getItemInMainHand().getItemMeta()).getDisplayName()
                        .replace("§1","").replace("§2","").replace("§3","").replace("§4","").replace("§5","").replace("§6","").replace("§7","").replace("§8","").replace("§9","").replace("§a","").replace("§c","").replace("§d","").replace("§b","").replace("§o","").replace("§l","").replace("§a",""));
                // 暴力去除彩色属性XD
            }
            catch (Exception e)
            {
                event.getEntity().getPlayer().sendMessage("{arm}: null");

            }
            event.getEntity().getPlayer().sendMessage(ChatColor.YELLOW+"+----------+");
        }
        int i=0;
        for(String str : list)
        {
            String bi1 = event.getDeathMessage();
            String bi2 = str.replace("{player}", Objects.requireNonNull(event.getEntity().getPlayer()).getName()+"");
            if(event.getEntity().getKiller()!=null) {
                bi2 = bi2.replace("{killer}", event.getEntity().getKiller().getName() + "");
            }
            try {
                bi2 = bi2.replace("{arm}", Objects.requireNonNull(Objects.requireNonNull(event.getEntity().getKiller().getEquipment()).getItemInMainHand().getItemMeta()).getDisplayName()
                        .replace("§1","").replace("§2","").replace("§3","").replace("§4","").replace("§5","").replace("§6","").replace("§7","").replace("§8","").replace("§9","").replace("§a","").replace("§c","").replace("§d","").replace("§b","").replace("§o","").replace("§l","").replace("§a",""));
                // 暴力彩色去除
            }
            catch (Exception ignored)
            {}
            if (Objects.equals(bi1, bi2))
            {
                String set = list.get(i + 1);
                if(event.getEntity().getKiller()!=null) set = set.replace("{killer}",event.getEntity().getKiller().getName()+"");
                try
                {
                    set = set.replace("{arm}", Objects.requireNonNull(Objects.requireNonNull(event.getEntity().getKiller().getEquipment()).getItemInMainHand().getItemMeta()).getDisplayName() + "");
                }
                catch (Exception ignored) {}
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                {
                    Random random_base = new Random(); //生成种子
                    String[] the_split = set.split("\\|"); //分割数据
                    int random_die = random_base.nextInt(the_split.length); //在0-分割数据的数量选取随机数
                    set=the_split[random_die]; //设置随机数输出
                    event.setDeathMessage(set.replace("{player}", event.getEntity().getPlayer().getName() + ""));
                    return;
                }
                else //如果没有papi
                {
                    Random random_base = new Random(); //生成种子
                    String[] the_split = set.split("\\|"); //分割数据
                    int random_die = random_base.nextInt(the_split.length); //在0-分割数据的数量选取随机数
                    set=the_split[random_die]; //设置随机数输出
                    set = PlaceholderAPI.setPlaceholders(event.getEntity().getPlayer(),set);
                    event.setDeathMessage(set.replace("{player}", event.getEntity().getPlayer().getName() + ""));
                    return;
                }
            }
            i++;
        }
    }
}

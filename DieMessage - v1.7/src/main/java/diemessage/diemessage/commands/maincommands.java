package diemessage.diemessage.commands;

import diemessage.diemessage.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;


public class maincommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command, String label,String[] args) {
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage reload - reload plugin(重载插件)");
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage info - look at plugin's info(查看插件配置)");
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage intercept - change the intercept(死亡消息拦截开关)");
        }
        else
        {
            if(args[0].equals("reload")&&sender.hasPermission("diemessage.admin")&&args.length==1)
            {
                config.reloadConfig();
                sender.sendMessage(ChatColor.YELLOW+"[DieMessage] "+ChatColor.GREEN+"Reload succeeded(插件重载成功)");

            }
            else if(args[0].equals("info"))
            {
                if(config.getConfig().getBoolean("update")) {
                    new UpdateChecker(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class), 91658).getVersion(version -> {
                        if (diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion().equalsIgnoreCase(version)) {
                            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"The plugin is up to date(插件已是最新版):"+version);
                        } else {
                            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"New version found(发现新版):"+ version+ "\nsuggest to update!https://www.spigotmc.org/resources/diemessage-custom-death-message.91658\n(请前往https://www.mcbbs.net/thread-1194349-1-1.html更新插件");
                        }
                    });
                }
                if(config.getConfig().getString("intercept").equals("true")) {
                    sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"intercept is true(死亡拦截目前开启)");
                }
                else sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"intercept is false(死亡拦截目前关闭)");
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                {
                    sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"PlaceholderAPI function has been accessed(papi已接入)");
                }
                else
                {
                    sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"PlaceholderAPI cannot be found, PlaceholderAPI feature has been automatically disabled(papi未接入)");
                }
            }
            else if(args[0].equals("intercept"))
            {
                String tf = config.getConfig().getString("intercept");
                if(tf.equals("true"))
                {
                    tf="false";
                    sender.sendMessage(ChatColor.YELLOW+"[DieMessage] "+ChatColor.GREEN+"Intercept is false now(死亡消息拦截关闭)");
                }
                else
                {
                    tf="true";
                    sender.sendMessage(ChatColor.YELLOW+"[DieMessage] "+ChatColor.GREEN+"Intercept is true now(死亡消息拦截开启)");
                }
                config.getConfig().set("intercept",tf);
                config.saveConfig();
            }
        }
        return false;
    }
}

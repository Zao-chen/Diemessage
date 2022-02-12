package diemessage.diemessage.commands;

import diemessage.diemessage.DieMessage;
import diemessage.diemessage.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class maincommands implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command, String label,String[] args) {
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        File aconfig = new File(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class).getDataFolder(),"settings.yml");
        FileConfiguration cconfig = YamlConfiguration.loadConfiguration(aconfig);
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage reload - reload plugin(重载插件)");
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage info - look at plugin's info(查看插件配置)");
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
                String synthesis_message = "====DieMessage==="; //合成消息
                if(Objects.equals(cconfig.getString("intercept"), "true"))
                {
                    if(cconfig.getString("langerous").equals("en")) synthesis_message += "\nintercept: true";
                    else synthesis_message += "\n死亡拦截: 开启";
                }
                else
                {
                    if(cconfig.getString("langerous").equals("en")) synthesis_message += "\nintercept: false";
                    else synthesis_message += "\n死亡拦截: 关闭";
                }
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                {
                    if(cconfig.getString("langerous").equals("en")) synthesis_message += "\nPapi: have";
                    else synthesis_message += "\nPapi: 拥有";
                }
                else
                {
                    if(cconfig.getString("langerous").equals("en")) synthesis_message += "\nPapi: no";
                    else synthesis_message += "\nPapi: 没有";
                }
                if(cconfig.getBoolean("update")) {
                    String finalSynthesis_message = synthesis_message;
                    new UpdateChecker(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class), 91658).getVersion(version -> {
                        if(cconfig.getString("langerous").equals("en")) sender.sendMessage(finalSynthesis_message+"\nnow version: "+ diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion()+"\nlatest version: "+version+"\n================");
                        else sender.sendMessage(finalSynthesis_message+"\n当前版本: "+ diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion()+"\n最新版本: "+version+"\n=================");
                    });
                }

            }
        }
        return false;
    }
    /*tab补全提示设置*/
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1)
        {
            List<String> list = new ArrayList<>();
            list.add("reload");
            list.add("info");
            return list;
        }
        return null;
    }
}

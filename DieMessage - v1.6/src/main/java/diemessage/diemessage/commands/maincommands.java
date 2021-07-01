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
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage reload - reload plugin");
            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"/diemessage info - look at plugin's info");
        }
        else
        {
            if(args[0].equals("reload")&&sender.hasPermission("diemessage.admin")&&args.length==1)
            {
                config.reloadConfig();
                sender.sendMessage(ChatColor.YELLOW+"[DieMessage] "+ChatColor.GREEN+"Reload succeeded.");

            }
            else if(args[0].equals("info"))
            {
                if(config.getConfig().getBoolean("update")) {
                    new UpdateChecker(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class), 91658).getVersion(version -> {
                        if (diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion().equalsIgnoreCase(version)) {
                            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-noupdatesfound")+version);
                        } else {
                            sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-findupdates")+version);
                        }
                    });
                }
                if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                {
                    sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-havepapi"));
                }
                else
                {
                    sender.sendMessage(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-nopapi"));
                }
            }
        }
        return false;
    }
}

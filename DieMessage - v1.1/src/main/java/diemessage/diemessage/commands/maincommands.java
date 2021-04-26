package diemessage.diemessage.commands;

import diemessage.diemessage.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;


public class maincommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command, String label,String[] args) {
        new UpdateChecker(diemessage.diemessage.DieMessage.getPlugin(diemessage.diemessage.DieMessage.class), 91658).getVersion(version -> {
            if (diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion().equalsIgnoreCase(version)) {
                sender.sendMessage("[Diemessage] 插件已是最新版本:"+diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class).getDescription().getVersion()+" "+version);
            } else {
                sender.sendMessage("[Diemessage] 发现新版本，建议前往更新!https://www.mcbbs.net/thread-1194349-1-1.html");
            }
        });
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        if(args[0].equals("reload")&&sender.hasPermission("diemessage.admin"))
        {
            config.reloadConfig();
            sender.sendMessage(ChatColor.YELLOW+"[DieMessage] "+ChatColor.GREEN+"Reload succeeded.");
        }
        return false;
    }
}

package diemessage.diemessage;

import diemessage.diemessage.commands.maincommands;
import diemessage.diemessage.events.Mainevent;
import diemessage.diemessage.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DieMessage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
        {
            logger.info("[Diemessage] 无法找到PlaceholderAPI,已自动禁用PlaceholderAPI功能");
        }
        else
        {
            logger.info("[Diemessage] PlaceholderAPI功能已接入");
        }
        int pluginId = 11123; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new Mainevent(),this);
        getCommand("diemessage").setExecutor(new maincommands());
        saveDefaultConfig();
        new UpdateChecker(this, 91658).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("[Diemessage] 插件已是最新版本!");
            } else {
                logger.info("[Diemessage] 发现新版本，建议前往更新!\n[Diemessage] https://www.mcbbs.net/thread-1194349-1-1.html");
            }
        });
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

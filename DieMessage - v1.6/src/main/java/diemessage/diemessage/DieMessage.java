package diemessage.diemessage;

import diemessage.diemessage.commands.maincommands;
import diemessage.diemessage.events.Mainevent;
import diemessage.diemessage.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DieMessage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            logger.info("[Diemessage] "+config.getConfig().getString("message-havepapi"));
        }
        else
        {
            logger.info("[Diemessage] "+config.getConfig().get("message-nopapi"));
        }
        int pluginId = 11123; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new Mainevent(),this);
        getCommand("diemessage").setExecutor(new maincommands());
        saveDefaultConfig();
        if(config.getConfig().getBoolean("update")) {
            new UpdateChecker(this, 91658).getVersion(version -> {
                if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-noupdatesfound")+version);
                } else {
                    logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+config.getConfig().getString("message-findupdates")+version);
                }
            });
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package diemessage.diemessage;

import diemessage.diemessage.commands.maincommands;
import diemessage.diemessage.events.Mainevent;
import diemessage.diemessage.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class DieMessage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Logger logger = this.getLogger();
        this.saveResource("settings.yml",false);
        Plugin config = diemessage.diemessage.DieMessage.getProvidingPlugin(diemessage.diemessage.DieMessage.class);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        {
            logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"PlaceholderAPI function has been accessed(papi已接入)");
        }
        else
        {
            logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"PlaceholderAPI cannot be found, PlaceholderAPI feature has been automatically disabled(papi未接入)");
        }
        int pluginId = 11123; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new Mainevent(),this);
        getCommand("diemessage").setExecutor(new maincommands());
        Objects.requireNonNull(getCommand("diemessage")).setTabCompleter(new maincommands());
        saveDefaultConfig();
        File aconfig = new File(this.getDataFolder(),"settings.yml");
        FileConfiguration cconfig = YamlConfiguration.loadConfiguration(aconfig);
        if(cconfig.getBoolean("update")) {
            new UpdateChecker(this, 91658).getVersion(version -> {
                if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                    logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"The plugin is up to date(插件已是最新版):"+version);
                } else {
                    logger.info(ChatColor.YELLOW+"[Diemessage] "+ChatColor.GREEN+"New version found(发现新版):"+ version+ "\nsuggest to update!https://www.spigotmc.org/resources/diemessage-custom-death-message.91658\n(请前往https://www.mcbbs.net/thread-1194349-1-1.html更新插件");
                }
            });
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

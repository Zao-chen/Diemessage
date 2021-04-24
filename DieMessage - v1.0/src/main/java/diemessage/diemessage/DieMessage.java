package diemessage.diemessage;

import diemessage.diemessage.commands.maincommands;
import diemessage.diemessage.events.Mainevent;
import diemessage.diemessage.metrics.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DieMessage extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        int pluginId = 11123; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        getServer().getPluginManager().registerEvents(new Mainevent(),this);
        getCommand("diemessage").setExecutor(new maincommands());
        saveDefaultConfig();
        Logger logger = this.getLogger();
        /*new UpdateChecker(this, 87065).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is not a new update available.");
            } else {
                logger.info("There is a new update available.");
            }
        });
         */
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

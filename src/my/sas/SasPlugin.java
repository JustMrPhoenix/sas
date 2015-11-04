package my.sas;

import com.earth2me.essentials.Essentials;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import my.sas.kit.KitExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SasPlugin extends JavaPlugin implements Listener {
    public SasConfig mainConfig;
    public FileConfiguration config;
    public PluginManager pluginManager;
    public KitExecutor kitExecutor;
    public InvKeeper invKeeper;
    public ItemHandler itemHandler;
    public SasCommandExecutor sasCommandExecuter;
    public AntiGodKill antiGodKill;
    public Essentials essentials;
    public WorldGuardPlugin worldGuard;
    public SasCommandBlock sasCommandBlock;
    public PermissionsEx pex;

    @Override
    public void onEnable() {
        config = getConfig();
        // TODO: Separate main config and db file correctly
        mainConfig = new SasConfig("db.yml", this);
        pluginManager = getServer().getPluginManager();
        essentials = (Essentials) pluginManager.getPlugin("Essentials");
        worldGuard = (WorldGuardPlugin) pluginManager.getPlugin("WorldGuard");
        pex = (PermissionsEx) pluginManager.getPlugin("PermissionsEx");
        kitExecutor = new KitExecutor(this);
        invKeeper = new InvKeeper();
        itemHandler = new ItemHandler(this, this.getClassLoader());
        sasCommandExecuter = new SasCommandExecutor(this, this.getClassLoader());
        antiGodKill = new AntiGodKill(essentials);
        sasCommandBlock = new SasCommandBlock(this);
        pluginManager.registerEvents(this, this);
        pluginManager.registerEvents(invKeeper, this);
        pluginManager.registerEvents(kitExecutor, this);
        pluginManager.registerEvents(itemHandler, this);
        pluginManager.registerEvents(antiGodKill, this);
        pluginManager.registerEvents(sasCommandBlock, this);
        getLogger().info("SAS plugin activated");
        mainConfig.setAutosave(true);
    }

    @Override
    public void onDisable() {
        System.out.println(mainConfig.toString());
        mainConfig.save();
        getLogger().info("SAS plugin deactivated");
    }
}
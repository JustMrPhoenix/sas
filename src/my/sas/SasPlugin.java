package my.sas;

import com.earth2me.essentials.Essentials;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import my.sas.kit.KitExecutor;
import my.sas.util.VaultUtil;
import net.milkbowl.vault.permission.Permission;
import ninja.leaping.permissionsex.PermissionsEx;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SasPlugin extends JavaPlugin implements Listener {
    private SasConfig dataBaseConfig;
    private FileConfiguration config;
    private PluginManager pluginManager;
    private KitExecutor kitExecutor;
    private ItemHandler itemHandler;
    private SasCommandExecutor sasCommandExecuter;
    private AntiGodKill antiGodKill;
    private Essentials essentials;
    private WorldGuardPlugin worldGuard;
    private PermissionsEx permissionsEx;
    private Permission permission;

    @Override
    public void onEnable() {
        config = getConfig();
        // TODO: Separate main config and dataBaseConfig file correctly
        dataBaseConfig = new SasConfig("dataBaseConfig.yml", this);
        pluginManager = getServer().getPluginManager();
        essentials = (Essentials) pluginManager.getPlugin("Essentials");
        worldGuard = (WorldGuardPlugin) pluginManager.getPlugin("WorldGuard");
        permissionsEx = (PermissionsEx) pluginManager.getPlugin("PermissionsEx");
        permission = VaultUtil.getPermissionsProvider();
        kitExecutor = new KitExecutor(this);
        itemHandler = new ItemHandler(this, this.getClassLoader());
        sasCommandExecuter = new SasCommandExecutor(this);
        antiGodKill = new AntiGodKill(essentials);
        pluginManager.registerEvents(this, this);
        pluginManager.registerEvents(kitExecutor, this);
        pluginManager.registerEvents(itemHandler, this);
        pluginManager.registerEvents(antiGodKill, this);
        getLogger().info("SAS plugin activated");
        dataBaseConfig.setAutosave(true);
    }

    @Override
    public void onDisable() {
        System.out.println(dataBaseConfig.toString());
        dataBaseConfig.save();
        getLogger().info("SAS plugin deactivated");
    }

    public SasCommandBase getSasCommand(String name) {
        return sasCommandExecuter.getSasCommand(name);
    }

    //region generated getters and setters

    public SasConfig getDataBaseConfig() {
        return dataBaseConfig;
    }

    public KitExecutor getKitExecutor() {
        return kitExecutor;
    }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }


    public AntiGodKill getAntiGodKill() {
        return antiGodKill;
    }

    public Essentials getEssentials() {
        return essentials;
    }

    public WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    public PermissionsEx getPermissionsEx() {
        return permissionsEx;
    }

    public Permission getPermission() {
        return permission;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    //endregion

}
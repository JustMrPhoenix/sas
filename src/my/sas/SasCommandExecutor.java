package my.sas;

import my.sas.commands.SasPing;
import my.sas.commands.SasVanish;
import my.sas.commands.SasWhoIs;
import my.sas.commands.SasWild;

import java.util.HashMap;

public class SasCommandExecutor {

    public SasPlugin plugin;
    public HashMap<String, SasCommandBase> commands = new HashMap<String, SasCommandBase>();

    public SasCommandExecutor(SasPlugin plugin) {
        this.plugin = plugin;

//        register(new SasClear(plugin));
        register(new SasPing(plugin));
        register(new SasVanish(plugin));
        register(new SasWhoIs(plugin));
        register(new SasWild(plugin));
    }

    private void register(SasCommandBase sasCommandBase) {
        commands.put(sasCommandBase.getCommand(), sasCommandBase);
        plugin.getPluginManager().registerEvents(sasCommandBase, plugin);
        for (String command : sasCommandBase.getCommands()) {
            plugin.getCommand(command).setExecutor(sasCommandBase);
        }
    }
}

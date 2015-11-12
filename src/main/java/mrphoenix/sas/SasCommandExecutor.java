package mrphoenix.sas;

import mrphoenix.sas.commands.SasPing;
import mrphoenix.sas.commands.SasVanish;
import mrphoenix.sas.commands.SasWhoIs;
import mrphoenix.sas.commands.SasWild;

import java.util.HashMap;

public class SasCommandExecutor {

    private SasPlugin plugin;
    // TODO: Maybe use singleton pattern instead of HashMap?
    private HashMap<String, SasCommandBase> commands = new HashMap<>();

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

    public SasCommandBase getSasCommand(String name) {
        return commands.get(name);
    }
}

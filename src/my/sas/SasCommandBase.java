package my.sas;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public abstract class SasCommandBase implements CommandExecutor, TabCompleter, Listener {
    protected SasPlugin plugin;
    protected String command;

    public SasCommandBase(SasPlugin plugin, String command) {
        this.plugin = plugin;
        this.command = command;
    }

    protected void onError(CommandSender sender, String error) {
        if (sender != null && sender instanceof Player) {
            Player ply = (Player) sender;
            ply.sendMessage(ChatColor.RED + " Ошибка! " + error);
        }
        System.out.println("[" + command + "] Error! " + error);
    }

    // TODO: Is this method really necessary? Consider removing and making onCommand abstract
    public abstract boolean run(CommandSender commandSender, Command command, String label, String[] args);

    // TODO: Same as with method "run"
    public abstract List<String> tab(CommandSender commandSender, Command command, String label, String[] args);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        return run(commandSender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (command.getName().equals(command)) {
            return tab(commandSender, command, label, args);
        } else {
            return null;
        }
    }

    public String getCommand() {
        return command;
    }
}

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

    @Override
    public abstract boolean onCommand(CommandSender commandSender, Command command, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }

    public String getCommand() {
        return command;
    }
}

package mrphoenix.sas;

import mrphoenix.sas.util.CollectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class SasCommandBase implements CommandExecutor, TabCompleter, Listener {
    protected SasPlugin plugin;
    // TODO: Refactor using org.bukkit.command.Command instead of String
    protected List<String> commands = new ArrayList<>();
    protected World defaultWorld = Bukkit.getWorld("world");

    public SasCommandBase(SasPlugin plugin, List<String> commands) {
        this.plugin = plugin;
        this.commands = CollectionUtil.requireNotEmpty(commands);
    }

    public SasCommandBase(SasPlugin plugin, String command) {
        this.plugin = plugin;
        commands.add(Objects.requireNonNull(command));
    }

    protected void onError(CommandSender sender, String error) {
        if (sender != null && sender instanceof Player) {
            Player ply = (Player) sender;
            ply.sendMessage(ChatColor.RED + " Ошибка! " + error);
        }
        System.out.println("[" + commands.get(1) + "] Error! " + error);
    }

    @Override
    public abstract boolean onCommand(CommandSender commandSender, Command command, String label, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }

    public String getCommand() {
        return commands.get(0);
    }

    public List<String> getCommands() {
        return commands;
    }
}

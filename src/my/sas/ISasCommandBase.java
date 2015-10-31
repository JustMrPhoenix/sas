package my.sas;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * Created by Mr.Phoenix on 10/31/2015.
 */
public abstract interface ISasCommandBase extends CommandExecutor, TabCompleter{
    public abstract boolean run( CommandSender commandSender, Command command, String string, String[] strings );
}

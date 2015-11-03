package my.sas;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Created by Mr.Phoenix on 9/13/2015.
 */
public abstract class SasCommandBase implements CommandExecutor, TabCompleter, Listener {

    protected SasPlugin plugin;
    protected String command;

    public SasCommandBase() {
    }

    public String getCommand( ){
        return command;
    }

    public SasCommandBase( SasPlugin p){
        this.plugin = p;
    }

    protected void err(CommandSender sender, String err){
        if( sender != null && sender instanceof Player){
            Player ply = ( Player ) sender;
            ply.sendMessage( ChatColor.RED+"Ошибка!"+err );
        }
        System.out.println("[" + command + "]Error!" + err);
    }

    protected void msg(CommandSender sender, String msg){
        sender.sendMessage(msg);
    }

    public abstract boolean run( CommandSender commandSender, Command command, String string, String[] strings );

    public abstract List<String> tab( CommandSender commandSender, Command command, String string ,String[] strings );

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return run(commandSender, command, s, strings);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if( command.getName().equals( command ) ){
            return tab( commandSender, command, s, strings );
        }else{
            return null;
        }
    }
}

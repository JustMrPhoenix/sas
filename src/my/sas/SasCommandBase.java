package my.sas;

import my.sas.SasPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 * Created by Mr.Phoenix on 9/13/2015.
 */
public class SasCommandBase implements CommandExecutor, Listener {

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
        if( sender instanceof Player){
            Player ply = ( Player ) sender;
            ply.sendMessage( ChatColor.RED+"Ошибка!"+err );
        }
        System.out.println( "["+command+"]Error!"+err );
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}

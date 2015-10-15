package my.sas.commands;

import my.sas.SasPlugin;
import my.sas.SasCommandBase;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Mr.Phoenix on 9/13/2015.
 */
public class SasPing extends SasCommandBase implements CommandExecutor {

    public SasPing(SasPlugin p) {
        this.plugin = p;
        this.command = "sasping";
    }

    public int getPing(Player p) {
        CraftPlayer cp = (CraftPlayer) p;
        EntityPlayer ep = cp.getHandle();
        return ep.ping;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if( ! ( commandSender instanceof Player) ){
            return true;
        }
        Player ply = ( Player )commandSender;
        ply.sendMessage( getPing(ply)+"ms!" );
        return true;
    }
}
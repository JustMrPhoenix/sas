package my.sas.commands;

import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SasPing extends SasCommandBase {
    public SasPing(SasPlugin p) {
        super(p, "sasping");
    }

    public int getPing(Player p) {
        CraftPlayer cp = (CraftPlayer) p;
        EntityPlayer ep = cp.getHandle();
        return ep.ping;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player ply = (Player) commandSender;
        ply.sendMessage(getPing(ply) + "ms!");
        return true;
    }
}

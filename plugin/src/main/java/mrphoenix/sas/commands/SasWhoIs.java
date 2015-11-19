package mrphoenix.sas.commands;

import com.earth2me.essentials.User;
import mrphoenix.sas.SasCommandBase;
import mrphoenix.sas.SasPlugin;
import mrphoenix.sas.user.UserInfo;
import mrphoenix.sas.user.UserInfoRow;
import mrphoenix.sas.util.LocationUtil;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class SasWhoIs extends SasCommandBase {
    public SasWhoIs(SasPlugin plugin) {
        super(plugin, "saswhois");
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getServer().getPlayer(args[0]);

            if (target == null) {
                return false;
            }

            reply(target, commandSender);
            return true;
        }

        return false;
    }

    public void reply(Player target, CommandSender commandSender) {
        UserInfo userInfo = new UserInfo();
        User essUser = plugin.getEssentials().getUser(target);

        userInfo.addRow(new UserInfoRow("Nickname: ", target.getName()));

        userInfo.addRow(new UserInfoRow("Health: ", target.getHealth() + "/" + target.getMaxHealth() + " ( +" + target.getFoodLevel() + " food level )"));

        userInfo.addRow(new UserInfoRow("Experience: ", target.getExp() + "(level " + target.getLevel() + " )"));

        userInfo.addRow(new UserInfoRow("Location: ", LocationUtil.formatWithColor(target.getLocation())));

        userInfo.addRow(new UserInfoRow("Money: ", essUser.getMoney().toString()));

        userInfo.addRow(new UserInfoRow("IP: ", target.getAddress().toString()));

        userInfo.addRow(new UserInfoRow("Game Mode: ", target.getGameMode().name()));

        userInfo.addRow(new UserInfoRow("Is God: ", essUser.isGodModeEnabled()));

        userInfo.addRow(new UserInfoRow("Is OP: ", target.isOp()));

        userInfo.addRow(new UserInfoRow("Is Flying: ", target.isFlying()));

        userInfo.addRow(new UserInfoRow("Is AFK: ", essUser.isAfk()));

        userInfo.addRow(new UserInfoRow("Is In Jail: ", essUser.isJailed()));

        userInfo.addRow(new UserInfoRow("Is Muted: ", essUser.isMuted()));

        userInfo.addRow(new UserInfoRow("Is In SasVanish: ", ((SasVanish) plugin.getSasCommand("sasvanish")).inVanish(target)));

        // TODO: Add auth me support

        userInfo.addRow(new UserInfoRow("Groups: ", StringUtils.join(plugin.getPermission().getPlayerGroups(target), ", ")));
        userInfo.addRow(new UserInfoRow("Permissions: ", StringUtils.join(PermissionsEx.getUser(target).getAllPermissions().get(null), ", ")));

        userInfo.send(commandSender);
    }
}

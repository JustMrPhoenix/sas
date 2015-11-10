package my.sas.commands;

import com.earth2me.essentials.User;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import my.sas.user.UserInfo;
import my.sas.user.UserInfoRow;
import my.sas.util.LocationUtil;
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

        userInfo.addRow(new UserInfoRow("Ник: ", target.getName()));

        userInfo.addRow(new UserInfoRow("Здоровье: ", target.getHealth() + "/" + target.getMaxHealth() + " ( +" + target.getFoodLevel() + " насыщение )"));

        userInfo.addRow(new UserInfoRow("Уровень: ", target.getExp() + "(уровень " + target.getLevel() + " )"));

        userInfo.addRow(new UserInfoRow("Местоположение: ", LocationUtil.format(target.getLocation())));

        userInfo.addRow(new UserInfoRow("Баланс: ", essUser.getMoney().toString()));

        userInfo.addRow(new UserInfoRow("IP: ", target.getAddress().toString()));

        userInfo.addRow(new UserInfoRow("Игровой режим: ", target.getGameMode().name()));

        userInfo.addRow(new UserInfoRow("В режиме бога: ", essUser.isGodModeEnabled()));

        userInfo.addRow(new UserInfoRow("OP: ", target.isOp()));

        userInfo.addRow(new UserInfoRow("Летает: ", target.isFlying()));

        userInfo.addRow(new UserInfoRow("AFK: ", essUser.isAfk()));

        userInfo.addRow(new UserInfoRow("В тюрьме: ", essUser.isJailed()));

        userInfo.addRow(new UserInfoRow("В муте: ", essUser.isMuted()));

        userInfo.addRow(new UserInfoRow("В сас ванише: ", ((SasVanish) plugin.getSasCommandExecuter().commands.get("sasvanish")).inVanish(target)));

        // TODO: Add auth me support

        userInfo.addRow(new UserInfoRow("Группы: ", StringUtils.join(plugin.getPermission().getPlayerGroups(target), ", ")));
        userInfo.addRow(new UserInfoRow("Пермишены: ", StringUtils.join(PermissionsEx.getUser(target).getAllPermissions().get(null), ", ")));

        userInfo.send(commandSender);
    }
}

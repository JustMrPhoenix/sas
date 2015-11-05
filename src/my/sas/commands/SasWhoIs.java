package my.sas.commands;

import com.earth2me.essentials.User;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import my.sas.user.UserInfo;
import my.sas.user.UserInfoRow;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.List;

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
        User essUser = plugin.essentials.getUser(target);

        //Nick
        userInfo.addRow(new UserInfoRow("Ник: ", target.getName()));
        //HP
        userInfo.addRow(new UserInfoRow("Здоровье: ", target.getHealth() + "/" + target.getMaxHealth() + " ( +" + target.getFoodLevel() + " насыщение )"));
        //XP
        userInfo.addRow(new UserInfoRow("Уровень: ", target.getExp() + "(уровень " + target.getLevel() + " )"));
        //location
        // TODO: Make output readable
        userInfo.addRow(new UserInfoRow("Местоположение: ", target.getLocation().toString()));
        //balance
        userInfo.addRow(new UserInfoRow("Баланс: ", essUser.getMoney().toString()));
        //IP
        userInfo.addRow(new UserInfoRow("IP: ", target.getAddress().toString()));
        //GameMode
        userInfo.addRow(new UserInfoRow("Игровой режим: ", target.getGameMode().name()));
        //GodMode
        userInfo.addRow(new UserInfoRow("В режиме бога: ", essUser.isGodModeEnabled()));
        //OP
        userInfo.addRow(new UserInfoRow("OP: ", target.isOp()));
        //Fly
        userInfo.addRow(new UserInfoRow("Летает: ", target.isFlying()));
        //AFK
        userInfo.addRow(new UserInfoRow("AFK: ", essUser.isAfk()));
        //InJail
        userInfo.addRow(new UserInfoRow("В тюрьме: ", essUser.isJailed()));
        //InMute
        userInfo.addRow(new UserInfoRow("В муте: ", essUser.isMuted()));
        //InSasVanish
        userInfo.addRow(new UserInfoRow("В сас ванише: ", ((SasVanish) plugin.sasCommandExecuter.commands.get("sasvanish")).inVanish(target)));
        //PEX
        userInfo.addRow(new UserInfoRow("Группы: ", StringUtils.join(PermissionsEx.getUser(target).getGroupsNames(), ", ")));
        userInfo.addRow(new UserInfoRow("Пермишены: ", StringUtils.join(PermissionsEx.getUser(target).getAllPermissions().get(null), ", ")));

        userInfo.send(commandSender);
    }
}

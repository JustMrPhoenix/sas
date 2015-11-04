package my.sas.commands;

import com.earth2me.essentials.User;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import my.sas.user.UserStatus;
import my.sas.user.UserStatusRow;
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

    @Override
    public boolean run(CommandSender commandSender, Command command, String label, String[] args) {
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

    @Override
    public List<String> tab(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }

    public void reply(Player target, CommandSender commandSender) {
        UserStatus userStatus = new UserStatus();
        User essUser = plugin.essentials.getUser(target);

        //Nick
        userStatus.addRow(new UserStatusRow("Ник: ", target.getName()));
        //HP
        userStatus.addRow(new UserStatusRow("Здоровье: ", target.getHealth() + "/" + target.getMaxHealth() + " ( +" + target.getFoodLevel() + " насыщение )"));
        //XP
        userStatus.addRow(new UserStatusRow("Уровень: ", target.getExp() + "(уровень " + target.getLevel() + " )"));
        //location
        // TODO: Make output readable
        userStatus.addRow(new UserStatusRow("Местоположение: ", target.getLocation().toString()));
        //balance
        userStatus.addRow(new UserStatusRow("Баланс: ", essUser.getMoney().toString()));
        //IP
        userStatus.addRow(new UserStatusRow("IP: ", target.getAddress().toString()));
        //GameMode
        userStatus.addRow(new UserStatusRow("Игровой режим: ", target.getGameMode().name()));
        //GodMode
        userStatus.addRow(new UserStatusRow("В режиме бога: ", essUser.isGodModeEnabled()));
        //OP
        userStatus.addRow(new UserStatusRow("OP: ", target.isOp()));
        //Fly
        userStatus.addRow(new UserStatusRow("Летает: ", target.isFlying()));
        //AFK
        userStatus.addRow(new UserStatusRow("AFK: ", essUser.isAfk()));
        //InJail
        userStatus.addRow(new UserStatusRow("В тюрьме: ", essUser.isJailed()));
        //InMute
        userStatus.addRow(new UserStatusRow("В муте: ", essUser.isMuted()));
        //InSasVanish
        userStatus.addRow(new UserStatusRow("В сас ванише: ", ((SasVanish) plugin.sasCommandExecuter.commands.get("sasvanish")).inVanish(target)));
        //PEX
        userStatus.addRow(new UserStatusRow("Группы: ", StringUtils.join(PermissionsEx.getUser(target).getGroupsNames(), ", ")));
        // TODO: Display only permissions added customly (not inherited from groups)
        // This commented line displays ALL the permissions of a player
//        userStatus.addRow(new UserStatusRow("Пермишены: ", StringUtils.join(PermissionsEx.getUser(target).getPermissions(target.getWorld().getName()), ", ")));

        userStatus.send(commandSender);
    }
}

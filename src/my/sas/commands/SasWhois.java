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

import java.util.ArrayList;
import java.util.List;

public class SasWhoIs extends SasCommandBase {
    public SasWhoIs(SasPlugin plugin) {
        super(plugin, "saswhois");
    }

    @Override
    public boolean run(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length > 0) {
            reply(Bukkit.getServer().getPlayer(args[0]), commandSender);
            return true;
        }

        return false;
    }

    @Override
    public List<String> tab(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }

    public boolean reply(Player player, CommandSender commandSender) {
        if (player == null) {
            return false;
        }

        UserStatus userStatus = new UserStatus();
        User essUser = plugin.essentials.getUser(player);

        //Nick
        userStatus.addRow(new UserStatusRow("Ник: ", player.getName()));
        //HP
        userStatus.addRow(new UserStatusRow("Здоровье: ", player.getHealth() + "/" + player.getMaxHealth() + " ( +" + player.getFoodLevel() + " насыщение )"));
        //XP
        userStatus.addRow(new UserStatusRow("Уровень: ", player.getExp() + "(уровень " + player.getLevel() + " )"));
        //location
        userStatus.addRow(new UserStatusRow("Местоположение: ", player.getLocation().toString()));
        //balance
        userStatus.addRow(new UserStatusRow("Баланс: ", essUser.getMoney().toString()));
        //IP
        userStatus.addRow(new UserStatusRow("IP: ", player.getAddress().toString()));
        //GameMode
        userStatus.addRow(new UserStatusRow("Игровой режим: ", player.getGameMode().name()));
        //GodMode
        userStatus.addRow(new UserStatusRow("В режиме бога: ", essUser.isGodModeEnabled()));
        //OP
        userStatus.addRow(new UserStatusRow("OP: ", player.isOp()));
        //Fly
        userStatus.addRow(new UserStatusRow("Летает: ", player.isFlying()));
        //AFK
        userStatus.addRow(new UserStatusRow("AFK: ", essUser.isAfk()));
        //InJail
        userStatus.addRow(new UserStatusRow("В тюрьме: ", essUser.isJailed()));
        //InMute
        userStatus.addRow(new UserStatusRow("В муте: ", essUser.isMuted()));
        //InSasVanish
        userStatus.addRow(new UserStatusRow("В сас ванише: ", ((SasVanish) plugin.sasCommandExecuter.commands.get("sasvanish")).inVanish(player)));
        //PEX
        userStatus.addRow(new UserStatusRow("Группы:", StringUtils.join(PermissionsEx.getUser(player).getGroupsNames(), ",")));
        userStatus.addRow(new UserStatusRow("Пермишены: ", StringUtils.join(PermissionsEx.getUser(player).getPermissions(player.getWorld().getName()), "")));

        userStatus.send(commandSender);

        return true;
    }
}

package my.sas.commands;

import com.earth2me.essentials.User;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import my.sas.util.SasUserStatusRow;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SasWhoIs extends SasCommandBase implements CommandExecutor {
    public SasWhoIs(SasPlugin plugin) {
        this.plugin = plugin;
        this.command = "saswhois";
    }

    public SasUserStatusRow getBool( String name, boolean aBoolean ){
        SasUserStatusRow sasUserStatusRow = new SasUserStatusRow( name );
        sasUserStatusRow.setBoolean( aBoolean );
        return sasUserStatusRow;
    }

    public SasUserStatusRow getString( String name, String string ){
        SasUserStatusRow sasUserStatusRow = new SasUserStatusRow( name );
        sasUserStatusRow.setString(string);
        return sasUserStatusRow;
    }

    private String concat( List<String> strings, String s ){
        String rt = "";
        boolean first = true;
        for( String str: strings ){
            if( !first ){
                rt = rt +s+ str;
            }else{
                rt = str;
                first = false;
            }
        }
        return rt;
    }

    public boolean reply( Player player,CommandSender commandSender ){
        if( player == null ){
            return false;
        }
        List<String> strings = new ArrayList<String>();
        User essUser = plugin.ess.getUser(player);
        boolean isPlayer = ( commandSender instanceof Player );
        //Yeah, looks like shit :c
        //Nick
        strings.add( getString( "Ник: ", player.getName() ).get(isPlayer) );
        //HP
        strings.add( getString( "Здоровье: ", player.getHealth()+"/"+player.getMaxHealth()+" ( +"+player.getFoodLevel()+" насыщение )" ).get(isPlayer) );
        //XP
        strings.add( getString( "Уровень: ", player.getExp()+"(уровень "+player.getLevel()+" )" ).get(isPlayer) );
        //location
        strings.add( getString( "Местоположение: ", player.getLocation().toString() ).get(isPlayer) );
        //balance
        strings.add(getString("Баланс: ", essUser.getMoney().toString()).get(isPlayer));
        //IP
        strings.add( getString( "IP: ", player.getAddress().toString() ).get(isPlayer) );
        //GameMode
        strings.add( getString( "Игровой режим: ", player.getGameMode().name() ).get(isPlayer) );
        //GodMode
        strings.add( getBool("В режиме бога: ", essUser.isGodModeEnabled()).get(isPlayer));
        //OP
        strings.add( getBool( "OP: ", player.isOp() ).get(isPlayer));
        //Fly
        boolean inFly = player.isFlying();
        SasUserStatusRow fly = new SasUserStatusRow( "Режим полёта: ");
        fly.setBoolean( inFly );
        if( inFly ){
            fly.setComment( "летает" );
        }else{
            fly.setComment( "не летает" );
        }
        strings.add( fly.get(isPlayer) );
        //AFK
        strings.add(getBool("AFK: ", essUser.isAfk()).get(isPlayer));
        //InJail
        strings.add( getBool( "В тюрьме: ", essUser.isJailed() ).get(isPlayer));
        //InMute
        strings.add( getBool( "В муте: ", essUser.isMuted() ).get(isPlayer) );
        //InSasVanish
        strings.add( getBool( "В сас ванише: ", ( (SasVanish) plugin.sasCommandExecuter.commands.get("sasvanish") ).inVanish( player ) ).get(isPlayer) );
        if( isPlayer ){
            Player ply = ( Player ) commandSender;
            ply.sendMessage( concat( strings, "\n" ) );
        }else{
            for( String str: strings ){
                msg( commandSender, str );
            }
        }
        return true;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        reply( Bukkit.getServer().getPlayer( strings[0] ), commandSender );
        return true;
    }
}

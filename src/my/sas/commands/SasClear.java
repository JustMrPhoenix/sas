package my.sas.commands;

import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Mr.Phoenix on 10/27/2015.
 */
public class SasClear extends SasCommandBase implements CommandExecutor {

    public SasClear(SasPlugin p) {
        this.plugin = p;
        this.command = "sasclear";
    }

    public int toInt( String string ){
        int rt = 1;
        try{
            rt = Integer.parseInt( string );
        }catch( Exception e){
            err( null, "Попыточка прировнять \""+string+"\" к числоу" );
        }
        return rt;
    }

    public Entity[]  getNearbyEntities(Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
            for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public boolean check( Entity entity, boolean check){
        if( check ) {
            return !entity.getType().equals( EntityType.PLAYER );
        }
        EntityType type = entity.getType();
        if( type.equals( EntityType.HORSE ) ) {
            Horse horse = (Horse) entity;
            if (horse.isLeashed() || horse.isCarryingChest()) {
                return false;
            }else {
                return true;
            }
        }
        if( type.equals( EntityType.WOLF ) ){
            if( ( (Wolf) entity ).isLeashed() ){
                return false;
            }else{
                return true;
            }
        }
        return true;
    }

    public int clear( Player player, int radius, boolean check ){
        Entity[] ents = getNearbyEntities( player.getLocation(), radius );
        int c = 0;
        for( Entity e: ents ){
            if( !check( e, check ) ){ continue; }
            e.remove();
            c = c + 1;
        }
        return c;
    }

    public int clearWorld( World world, boolean check ){
        List<Entity> ents = world.getEntities();
        int c = 0;
        for( Entity e: ents ){
            if( !check( e, check ) ){ continue; }
            e.remove();
            c = c + 1;
        }
        return c;
    }

    @Override
    public boolean run(CommandSender commandSender, Command command, String s, String[] strings) {
        boolean check = false;
        if( strings.length > 0 && strings[strings.length-1].equalsIgnoreCase("-a") ){
            check = true;
            strings = Arrays.copyOf(strings, strings.length - 1 );
        }
        System.out.println( "Check "+check );
        int removed = 0;
        if( strings.length == 0 ){
            if( commandSender instanceof Player ) {
                removed = clearWorld( ( (Player) commandSender ).getWorld(), check );
            }else{
                for( World w: commandSender.getServer().getWorlds() ){
                    removed = clearWorld( w, check );
                }
            }
        }else if( strings.length==1 ){
            Player player = commandSender.getServer().getPlayer( strings[0] );
            if( player == null ){
                if( commandSender instanceof Player ) {
                    int range = toInt(strings[0]);
                    removed = clear( ((Player) commandSender) , range, check );
                }else{
                    err( commandSender, "Go fuck yourself!!" );
                }
            }else{
                removed = clearWorld( player.getWorld(), check );
            }
        }else if( strings.length==2 ){
            Player player = commandSender.getServer().getPlayer(strings[0]);
            if( player == null ){
                err( commandSender, "Go fuck yourself!!" );
            }else{
                int range = Integer.parseInt(strings[1]);
                removed = clear(player, range, check);
            }
        }
        msg( commandSender, "Я почистиль "+removed+" енитей c:" );
        return true;
    }

    @Override
    public List<String> tab(CommandSender commandSender, Command command, String string, String[] strings) {
        return null;
    }
}

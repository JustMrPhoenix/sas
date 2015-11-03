package my.sas.commands;

import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class SasClear extends SasCommandBase {
    public SasClear(SasPlugin plugin) {
        super(plugin, "sasclear");
    }

    public int toInt( String string ){
        int rt = 1;
        try{
            rt = Integer.parseInt( string );
        }catch( Exception e){
            onError(null, "Попыточка прировнять \"" + string + "\" к числоу");
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
        Entity[] ents = getNearbyEntities(player.getLocation(), radius);
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
    public boolean run(CommandSender commandSender, Command command, String label, String[] args) {
        boolean check = false;
        if( args.length > 0 && args[args.length-1].equalsIgnoreCase("-a") ){
            check = true;
            args = Arrays.copyOf(args, args.length - 1 );
        }
        System.out.println( "Check "+check );
        int removed = 0;
        if( args.length == 0 ){
            if( commandSender instanceof Player ) {
                removed = clearWorld( ( (Player) commandSender ).getWorld(), check );
            }else{
                for( World w: commandSender.getServer().getWorlds() ){
                    removed = clearWorld( w, check );
                }
            }
        }else if( args.length==1 ){
            Player player = commandSender.getServer().getPlayer( args[0] );
            if( player == null ){
                if( commandSender instanceof Player ) {
                    int range = toInt(args[0]);
                    removed = clear( ((Player) commandSender) , range, check );
                }else{
                    onError(commandSender, "Go fuck yourself!!");
                }
            }else{
                removed = clearWorld( player.getWorld(), check );
            }
        }else if( args.length==2 ){
            Player player = commandSender.getServer().getPlayer(args[0]);
            if( player == null ){
                onError(commandSender, "Go fuck yourself!!");
            }else{
                int range = Integer.parseInt(args[1]);
                removed = clear(player, range, check);
            }
        }
        commandSender.sendMessage("Удалено сущностей: " + removed + ".");
        return true;
    }

    @Override
    public List<String> tab(CommandSender commandSender, Command command, String label, String[] args) {
        return null;
    }
}

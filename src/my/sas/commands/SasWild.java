package my.sas.commands;

import my.sas.useless.Point;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * Created by Mr.Phoenix on 9/28/2015.
 */
public class SasWild extends SasCommandBase implements CommandExecutor {
	public WorldGuardPlugin wg;

	public SasWild( SasPlugin plugin){
		this.plugin = plugin;
		this.command = "wild";
		this.wg = plugin.wg;
	}

	@Override
	public List<String> tab(CommandSender commandSender, Command command, String string, String[] strings) {
		return null;
	}

	public boolean checkLocation( Location loc)
	{
		if (loc.getBlock().getType() != Material.AIR && loc.getBlock().getType() != null) {
			return false;
		}
		return true;
	}

	public Location getRandomLocation( World world, Point min, Point max ){
		Random Xrand = new Random();
		int x = Xrand.nextInt(max.getX() - min.getX()) + min.getX();
		Random Zrand = new Random();
		int z = Zrand.nextInt(max.getY() - min.getY()) + min.getY();
		int y = Bukkit.getServer().getWorld("world").getHighestBlockYAt(( int )x, (int) z);
		Location loc = new Location( world, x, y, z);
		return loc;
	}

	private void tpToRandomPos( Player player, int try_number){
		if( try_number >= 10 ){
			player.sendMessage( ChatColor.RED+"Достигнуто максимальное количество попыток!");
			return;
		}
		Location spawn = Bukkit.getServer().getWorld("world").getSpawnLocation();
		int spawn_x = spawn.getBlockX();
		int spawn_z = spawn.getBlockZ();
		Point min = new Point( spawn_x-6000, spawn_z-6000 );
		Point max = new Point( spawn_x+6000, spawn_z+6000 );
		Location loc = getRandomLocation( player.getWorld(), min, max );
		Chunk c = loc.getChunk();
		boolean loaded = c.isLoaded();
		if( !loaded ){
			c.load();
		}
		if ( !checkLocation(loc) || !wg.canBuild( player, loc ) || spawn.distance( loc ) < 1500 ) {
			player.sendMessage(ChatColor.RED + "Безопасное место для телепорта не найдено!Пробуем ещё раз...");
			tpToRandomPos(player, try_number +1);
			if( !loaded ){
				c.unload();
			}
		}else {
			player.teleport(loc);
			player.sendMessage(ChatColor.DARK_AQUA + "Телерортирование на: X: " + ChatColor.GOLD + loc.getX() + ChatColor.DARK_AQUA + " Z: " + ChatColor.GOLD + loc.getZ());
		}
	}

	@Override
	public boolean run(CommandSender commandSender, Command command, String s, String[] strings) {
		if( !( commandSender instanceof Player ) ){
			plugin.getLogger().warning( "RLY?" );
			return true;
		}
		Player player = ( Player ) commandSender;
		tpToRandomPos( player, 0 );
		return true;
	}
}

package my.sas.kit;

import my.sas.SasPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class KitExecutor implements Listener {
	public HashMap<String,Kit> kits = new HashMap<String,Kit>( );
	public KitCommandExecutor commandExecutor;
	private SasPlugin plugin;
	private ConfigurationSection config;

	public KitExecutor( SasPlugin plugin )
	{
		this.plugin = plugin;
		commandExecutor = new KitCommandExecutor( plugin, this );
		plugin.getCommand( "saskit" ).setExecutor( commandExecutor );
		plugin.getCommand( "addsign" ).setExecutor( commandExecutor );
		plugin.getCommand( "delsign" ).setExecutor( commandExecutor );
		plugin.getCommand( "addkit" ).setExecutor( commandExecutor );
		plugin.getLogger().info("Sas kit executor initialized.");
		if (plugin.getDataBaseConfig().getFileConfiguration().contains("kits"))
		{
			config = plugin.getDataBaseConfig().getSection("kits");
			Map<String,Object> data = config.getValues( false );
			deserialize(data);
		}
	}
	
	public boolean addKit( String kitname, Kit kit )
	{
		if( !kits.containsKey( kitname ) )
		{
			kits.put( kitname, kit );
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private String[] getLines( Block s )
	{
		if( ( s.getType( )!=Material.SIGN  && s.getType( )!= Material.WALL_SIGN ) || s == null )
		{
			return null;
		}
		Sign si = ( Sign ) s;
		return si.getLines( );
	}
	
	private boolean isChest( Block b )
	{
		if( b!= null && ( b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST ) )
		{
			return true;
		}
		return false;
	}
	
	private Kit hasKit( Block sign )
	{
		if( sign == null )
		{
			return null;
		}
		if( isChest( sign ) )
		{
			return chestContainsKit( sign );
		}
		if( sign.getType() != Material.WALL_SIGN )
		{
			return null;
		}	
		for( Kit kit : kits.values() )
		{	
			if( kit.hasSign( sign ) )
			{
				return kit;
			}
		}
		return null;
	}
	
	private Kit chestContainsKit( Block chest )
	{
		if( chest == null || !isChest( chest ) )
		{
			return null;
		}
		for( Map.Entry<String, Kit> entry : kits.entrySet() )
		{
			Kit kit = entry.getValue();
			if( kit.getChest( ).equals( chest ) )
			{
				return kit;
			}
		}
		return null;
	}
	
	private Block isKitable( Block sign )
	{
		if( sign == null || sign.getType() != Material.WALL_SIGN  )
		{
			return null;
		}
		Location loc = sign.getLocation();
		World world = loc.getWorld( );
		
		int x = loc.getBlockX( );
		int y = loc.getBlockY( );
		int z = loc.getBlockZ( );

		switch ( sign.getData( ) )
		{
			case 3:
				z = z - 1;
			break;
			
			case 4:
				x = x + 1;
			break;
			
			case 2:
				z = z + 1;
			break;
			
			case 5:
				x = x - 1;
			break;
			
		}
		
		Block block = world.getBlockAt(x, y, z);

		if( isChest( block ) )
		{
			return block;
		}
		else
		{
			return null;
		}
	}
	
	public Map<String,Object> serialize( )
	{
		if( kits.size() == 0 )
		{
			return null;
		}
		Map<String,Object> data = new HashMap( );
		for( Entry<String,Kit> entry : kits.entrySet() )
		{
			Kit kit = entry.getValue( );
			if( kit.getState( ) == "ready" )
			{
				data.put( entry.getKey( ), entry.getValue( ).serialize( ) );
			}
		}
		return data;
	}
	public void save( )
	{
		Map<String,Object> map = serialize( );
		if( map != null )
		{
			plugin.getDataBaseConfig().set("kits", map);
		}
	}
	public void deserialize( Map<String,Object> data )
{
	for( Entry<String,Object> e : data.entrySet( ) )
	{
		MemorySection section = ( MemorySection ) e.getValue( );
		Kit kit = Kit.deserialize( section.getValues( false ),e.getKey( ) );
		boolean state = kit.setState( "ready" );
		if( state )
		{
			kits.put( e.getKey() , kit );
		}
		else
		{
			System.out.println( "[SAS kit executor]Cant deserialize kit "+e.getKey( ) );
		}
	}
}
	@EventHandler
	public void onSignChange( SignChangeEvent e)
	{	
		Block sign = e.getBlock( );
		Block ik = isKitable( sign );
		String[] lines = e.getLines( );
		if( ik!=null && lines[0].equalsIgnoreCase("kit") )
		{
			String line = lines[1];
			Player ply = e.getPlayer( );
			if( line!= null && !line.equalsIgnoreCase( "" ) && ply.hasPermission( "kit.modify" ) && !kits.containsKey( line ) )
			{
				kits.put( line, new Kit( null, sign, line, null ) );
				ply.playSound( ply.getLocation ( ), Sound.ENDERMAN_TELEPORT, 10, 1);
				ply.sendMessage( "Набор "+line+" готов к использыванию!" );
			}
			else if( line!=null && kits.containsKey( line ) )
			{
				ply.sendMessage( ChatColor.RED+"Набор с именем "+line+" уже существует!" );
				sign.breakNaturally( );
			}
			else if( !ply.hasPermission( "kit.modify" ) )
			{
				sign.breakNaturally( );
				ply.sendMessage( "Вы не можете создавать наборы" );
			}
			else if( line == null || line.equalsIgnoreCase( "" ) )
			{
				e.getPlayer( ).sendMessage( ChatColor.RED+"Вы не указали имя набора!" );
				sign.breakNaturally( );
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract( PlayerInteractEvent e )
	{
		Player ply = e.getPlayer();
		Block block = e.getClickedBlock();
		if( e.getAction() == Action.RIGHT_CLICK_BLOCK &&  block!= null && block.getType( ) == Material.WALL_SIGN )
		{
			Kit kit = hasKit( block );
			//System.out.println( "Block:"+block+"||Kit:"+kit );	
			if( kit != null )
			{
				if( kit.hasPermission( ply, block ) )
				{
					kit.giveTo( ply );
				}
				else
				{
					ply.sendMessage( "У вас нет доступа к этому набору" );
				}
			}
		}
		if( e.getAction() == Action.RIGHT_CLICK_BLOCK && isChest( block )  )
		{
			Kit kit = chestContainsKit( block );
			//System.out.println( e.getAction( ) + "|"+e.getClickedBlock( )+"|"+kit );
			if( kit != null )
			{
				if( !ply.hasPermission( "kit.modify" ) )
				{
					e.setCancelled( true );
					ply.sendMessage( ChatColor.RED+"Вы не можете изменять наборы!" );
				}
			}
		}
	}
	@EventHandler
	public void onBlockBreak( BlockBreakEvent e )
	{
		Block block = e.getBlock( );
		Player ply = e.getPlayer( );
		Kit kit = hasKit( block );
		if( kit != null )
		{
			if( ply==null || !ply.hasPermission( "kit.modify" ) )
			{
				e.setCancelled( true );
				ply.sendMessage( ChatColor.RED+"В не можете изменять/удалять наборы" );
			}
			else if ( isChest( block ) )
			{
				ply.sendMessage( ChatColor.RED+"Набор уничтожен");
				kits.values( ).remove( kit );
				kit.remove( );
			}
			else
			{
				ply.sendMessage( ChatColor.GREEN+"Табличка удалена" );
				kit.removeSign( block );
			}
		}
	}
}

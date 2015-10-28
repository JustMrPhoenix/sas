package my.sas.kit;

import my.sas.SasPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommandExecutor implements CommandExecutor {

	private KitExecutor executor;
	private SasPlugin plugin;
	
	public KitCommandExecutor( SasPlugin p, KitExecutor ex )
	{
		executor = ex;
		plugin = p;
	}
	private boolean commandError( Player ply, String text )
	{
		if( ply != null) {
			ply.sendMessage( ChatColor.RED+text );
		}
		plugin.getLogger( ).info( "Command was not executed!Reason:"+text );
		return true;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,String[] args) 
	{
		String com = cmd.getName( );
		Player ply = null;
		if( sender instanceof Player )
		{
			ply = ( Player ) sender;
		}
		if( com.equalsIgnoreCase( "saskit" ) )
		{
			if( args.length < 1)
			{
				return false;
			}
			else if( args.length == 1)
			{
				if( ply == null )
				{
					return commandError( null ,"У тебя даже интвенторя нет:D" );
				}
				String kitname = args[0];
				if( !executor.kits.containsKey( kitname ) )
				{
					return commandError( ply, "Набор не найден" );
				}
				else
				{
					Kit kit = executor.kits.get( kitname );
					if( kit.hasPermission( ply, null ) ){
						kit.giveTo( ply );
					}
					else
					{
						return commandError( ply, "У Вас нет доступа к этому набору" );
					}
				}
			}
			else if( args.length == 2 )
			{
				String kitname = args[1];
				Player target = plugin.getServer( ).getPlayer( args[0] );
				if( !executor.kits.containsKey( kitname ) )
				{
					return commandError( ply, "Такого набора нет");
				}
				Kit kit = executor.kits.get( kitname );
				if( ply!=null &&( sender.hasPermission( "kit.others" ) || !kit.hasPermission( ply, null ) ) )
				{
					return commandError( ply, "У вас нет доступа к этой команде!" );
				}
				if( target == null )
				{
					return commandError( ply, "Игрок не найден!" );
				}
				executor.kits.get( kitname ).giveTo( target );
			}
			return true;
		}
		else if( com.equalsIgnoreCase( "addsign" ) && args.length>0 )
		{
			if( executor.kits.containsKey( args[0] ) )
			{
				Kit kit = executor.kits.get( args[0] );
				Block sign = ply.getTargetBlock( ( java.util.Set )null, 15 );
				if( sign == null )
				{
					commandError( ply, "Табличка не найдена!" );
				}
				else if( args.length == 2)
				{
					if( kit.addSign( sign, args[1] ) )
					{
						ply.sendMessage( ChatColor.GREEN+"Табличка добавлена!Тег доступа:\""+ChatColor.BLUE+args[1]+ChatColor.GREEN+"\"." );
					}
					else
					{
						commandError( ply, "Ошибка при добавлении таблички!" );
					}
				}
				else
				{
					if( kit.addSign( sign, null ) )
					{
						ply.sendMessage( ChatColor.GREEN+"Табличка добавлена!" );
					}
					else
					{
						commandError( ply, "Ошибка при добавлении таблички!" );
					}
				}
			}
			else
			{
				commandError( ply, "Набор не найден!" );
			}
			return true;
		}
		else if( com.equalsIgnoreCase( "delsign" ) && args.length == 1 )
		{
			if( executor.kits.containsKey( args[0] ) )
			{
				Kit kit = executor.kits.get( args[0] );
				Block sign = ply.getTargetBlock( ( java.util.Set )null, 15 );
				if( sign == null )
				{
					commandError( ply, "Табличка не найдена!" );
					return true;
				}
				if( kit.removeSign( sign ) )
				{
					ply.sendMessage( ChatColor.GREEN+"Табличка удалена из набора "+ChatColor.BLUE+args[1]+ChatColor.GREEN+"." );
				}
				else
				{
					commandError( ply, "Ошибка при удалении таблички из набора "+ChatColor.BLUE+args[1]+ChatColor.GREEN+"!" );
				}
			}
			else
			{
				commandError( ply, "Набор не найден!" );
			}
			return true;
		}
		else if( com.equalsIgnoreCase("addkit") && args.length == 1 )
		{
			if( !executor.kits.containsKey( args[0] ) )
			{
				Block chest = ply.getTargetBlock( ( java.util.Set )null, 15 );
				if( chest != null && ( chest.getType( ) == Material.CHEST || chest.getType() == Material.CHEST ) )
				{
					Kit kit = new Kit( chest, null, args[0], null );
					executor.kits.put( args[0], kit );
					if( kit.getState( ).equalsIgnoreCase( "ready" ) )
					{
						ply.sendMessage( ChatColor.GREEN+"Набор создан успешно!" );
					}
					else
					{
						ply.sendMessage( ChatColor.RED+"Не ебу что тут может случиться.Крч набор сломался.Попробуй ещё разок чтоль:D" );
					}
				}
				else
				{
					commandError( ply, "Сундук не найден!" );
				}
			}
			else
			{
				commandError( ply, "Набор с таки имнем уже существует" );
			}
			return true;
		}
		return false;
	}
}

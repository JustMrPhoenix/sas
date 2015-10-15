package my.sas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

public class SasItemCommandExecutor implements CommandExecutor {

	private ItemHandler itemHandler;
	
	public SasItemCommandExecutor( ItemHandler ih ){
		this.itemHandler = ih;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String param, String[] params) {
		if( !(sender instanceof  Player) ){
			if( params.length == 2 ){
				Player ply = Bukkit.getPlayer( params[1] );
				if( ply == null ){
					System.out.println( "Игрок не найден :c" );
					return true;
				}
				if( !itemHandler.items.containsKey( params[0] ) ){
					System.out.println(" ЧЁ ЗА ВЕЩ?");
					return true;
				}
				SasItemBase base = ( SasItemBase ) itemHandler.items.get( params[0] );
				ply.getInventory().addItem( base.item() );
				itemHandler.checkItem( ply, ply.getItemInHand() );
				return true;
			}else {
				System.out.println("U r not even a player");
			}
			return true;
		}
		Player ply = ( Player ) sender;
		String command = cmd.getName();
		if( params.length == 1 ){
			if( !itemHandler.items.containsKey( params[0] ) ){
				ply.sendMessage(ChatColor.RED + " ЧЁ ЗА ВЕЩ?");
				return true;
			}
			SasItemBase base = ( SasItemBase ) itemHandler.items.get( params[0] );
			ply.getInventory().addItem( base.item() );
			itemHandler.checkItem( ply, null );
			return true;
		}
		return false;
	}

}

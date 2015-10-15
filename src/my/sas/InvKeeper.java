package my.sas;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class InvKeeper implements Listener
{
	public InvKeeper( ) { }
	@EventHandler
	public void onPlayerDeath( PlayerDeathEvent e )
	{
		Player ply = e.getEntity( );
		if( ply!= null )
		{
			if( ply.hasPermission( "sas.keepInv" ) )
			{
				e.setKeepInventory( true );
			}
			if( ply.hasPermission( "sas.keepXP" ) )
			{
				e.setKeepLevel( true );
			}
		}
	}
}

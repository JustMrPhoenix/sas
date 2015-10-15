package my.sas.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import my.sas.SasItemBase;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Analrod extends SasItemBase{
	
	public Analrod( String id ){
		this.id = id;
		this.material = Material.BLAZE_ROD;
		this.name = "Analrod";
		this.lore = "U CAN REKT UR ENEMY!";
	}
	
	public void onEvent( PlayerInteractEvent e ){
		Player ply = e.getPlayer();
		Action act = e.getAction();
		World world = ply.getWorld();
		Block b = e.getClickedBlock();
		if( b == null ){
			b = ply.getTargetBlock((java.util.Set) null, 200);
		}
		switch (e.getAction().name()){
			case "RIGHT_CLICK_AIR":
				if( ply.isSneaking() ){
					Location l = b.getLocation();
					Creeper ent  = ( Creeper ) b.getWorld( ).spawnEntity( l, EntityType.CREEPER );
					ent.setPowered( true );
					ent.setCustomName( "BOOM!" );
				}else{
					world.strikeLightning( b.getLocation() );
				}
				break;
			case "RIGHT_CLICK_BLOCK":
				if( ply.isSneaking() ){
					Location l = b.getLocation();
					TNTPrimed ent  = ( TNTPrimed ) b.getWorld( ).spawnEntity( l, EntityType.PRIMED_TNT );
					ent.setFuseTicks(10);
					ent.setCustomName( "BOOM!" );
				}else{
					world.strikeLightning( b.getLocation() );
				}
				break;
			case "LEFT_CLICK_BLOCK":
				b.breakNaturally();
				break;
			case "LEFT_CLICK_AIR":
				Vector dir = b.getLocation().toVector().subtract( ply.getEyeLocation().toVector() );
				Fireball ent = (Fireball) ply.getWorld( ).spawnEntity( ply.getEyeLocation( ).add( dir.divide( new Vector( 10, 10, 10 ) ) ), EntityType.FIREBALL );
				ent.setDirection( dir );
				ent.setBounce( true );
				break;
		}
	}

	public void onDamage( EntityDamageByEntityEvent e ) {
		Entity ent = e.getEntity();
		if (ent instanceof Player) {
			Player ply = ( Player ) ent;
			ply.addPotionEffect( new PotionEffect( PotionEffectType.HUNGER, 150, 2 ) );
			ply.addPotionEffect( new PotionEffect( PotionEffectType.WEAKNESS, 150, 2 ) );
			ply.addPotionEffect( new PotionEffect( PotionEffectType.WITHER, 150, 2 ) );
			ply.addPotionEffect( new PotionEffect( PotionEffectType.BLINDNESS, 150, 2 ) );
		}else{
			if( ( ( Player )e.getDamager() ).isSneaking() ){
				ent.remove();
			}else{
				e.setDamage( 99999999 );
			}
		}
	}
}

package mrphoenix.sas;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.Map;

public class AntiGodKill implements Listener {
	Essentials ess;
	Map<Player, Integer> warns = new HashMap<Player, Integer>();

	public AntiGodKill(Essentials ess) {
		this.ess = ess;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		warns.put(e.getPlayer(), 0);
	}

	private boolean needToWarn( Player ply ){
		User user = ess.getUser(ply);
		return ( !ply.hasPermission( "sas.protect.ignore" ) && ( user.isGodModeEnabled() || ply.getGameMode().equals( GameMode.CREATIVE ) || ply.isFlying() || user.isVanished() ) );
	}

	private Integer warn(Player ply) {
		ply.sendMessage(ChatColor.RED + "Вы находитесь в режиме бога/креативе/полёте!");
		Integer i = warns.get(ply);
		if (i == null) {
			i = 1;
		}
		if (i >= 30) {
			ply.kickPlayer("Многочисленные попытки ударить игрока находясь в режиме бога/креативе/полёте!");
		} else {
			warns.put(ply, i + 1);
		}
		return i;
	}

	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
		ThrownPotion thrownPotion = e.getPotion();
		if (!( thrownPotion.getShooter() instanceof Player)) {
			return;
		}
		Player damager = ( Player ) thrownPotion.getShooter();
		if ( needToWarn( damager )) {
			e.setCancelled(true);
			warn( damager );
		}
	}

	@EventHandler
	public void OnEntityDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player damager = null;
		if (e.getDamager() instanceof Player) {
			damager = (Player) e.getDamager();
		} else if (e.getDamager() instanceof Projectile) {
			ProjectileSource shooter = ((Projectile) e.getDamager()).getShooter();
			if (shooter instanceof Player) {
				damager = (Player) shooter;
			}
		} else if( e.getDamager() instanceof ThrownPotion ){
			ProjectileSource shooter = ( ( ThrownPotion) e.getDamager() ).getShooter();
			if (shooter instanceof Player) {
				damager = (Player) shooter;
			}
		}
		if (damager == null) {
			return;
		}
		if ( needToWarn( damager )) {
			e.setCancelled(true);
			warn( damager );
		}
	}
}

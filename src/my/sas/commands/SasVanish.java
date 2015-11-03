package my.sas.commands;

import com.earth2me.essentials.Essentials;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

/**
 * Created by Mr.Phoenix on 9/13/2015.
 */
public class SasVanish extends SasCommandBase implements TabCompleter {
	private List<String> inVanish = new ArrayList<String>();
	private Map<Player, Block> inventories = new HashMap<Player, Block>();
	Essentials essentials;
	public SasVanish(SasPlugin plugin) {
		this.plugin = plugin;
		this.command = "sasvanish";
		this.essentials = plugin.ess;
	}

	public void hide(Player ply) {
		for (Player p : ply.getWorld().getPlayers()) {
			if (!p.hasPermission("sas.vanish.see")) {
				p.hidePlayer(ply);
			}
		}
		essentials.getUser( ply ).setHidden(true);
		ply.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 1, false));
		ply.sendMessage(ChatColor.GREEN + "Now u r INVISIBLE BITCH");
		plugin.getLogger().info( ply.getDisplayName()+" is now invisible" );
	}

	public void show(Player ply) {
		for (Player p : ply.getWorld().getPlayers()) {
			if (!p.hasPermission("sas.vanish.see")) {
				p.showPlayer(ply);
			}
		}
		essentials.getUser( ply ).setHidden(false);
		ply.removePotionEffect(PotionEffectType.INVISIBILITY);
		ply.sendMessage(ChatColor.GREEN + "Now u r VISIBLE BITCH");
		plugin.getLogger().info(ply.getDisplayName() + " is now visible");
	}

	public boolean vanish(String nick) {
		if (nick == null) {
			return true;
		}
		if (inVanish.contains(nick)) {
			return true;
		}
		inVanish.add(nick);
		Player ply = plugin.getServer().getPlayer(nick);
		plugin.getLogger().info(nick + " is now in sasvanish");
		if (ply != null) {
			hide(ply);
		}
		return false;
	}

	public boolean vanishPly(Player ply) {
		if (inVanish(ply)) {
			return true;
		}
		inVanish.add(ply.getName());
		hide(ply);
		return false;
	}

	public boolean unVanish(String nick) {
		if (nick == null) {
			return true;
		}
		if (!inVanish.contains(nick)) {
			return true;
		}
		inVanish.remove(nick);
		Player ply = plugin.getServer().getPlayer(nick);
		plugin.getLogger().info(nick + " is no longer in sasvanish");
		if (ply != null) {
			show(ply);
		}
		return false;
	}

	public boolean unVanishPly(Player ply) {
		if (!inVanish(ply)) {
			return true;
		}
		inVanish.remove(ply.getName());
		show(ply);
		return false;
	}

	public boolean inVanish(Player ply) {
		return inVanish.contains(ply.getName());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player ply = e.getPlayer();
		if (inVanish(ply)) {
			hide(ply);
			e.setJoinMessage("");
		}
		if (!ply.hasPermission("sas.vanish.see")) {
			for (Player p : ply.getWorld().getPlayers()) {
				if (inVanish(p)) {
					ply.hidePlayer(p);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if (inVanish(player)) {
			e.setQuitMessage("");
			e.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (inVanish(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		if (inVanish( e.getPlayer() ) && !e.getPlayer().isSneaking() ) {
			e.setCancelled(true);
		}
	}

	public Inventory openVirtualInv( Block b, Player ply ){
		if( inventories.containsValue(b) ){
			Inventory inv = getByInv( b ).getOpenInventory().getTopInventory();
			ply.openInventory( inv );
			return inv;
		}
		Chest c = (Chest) b.getState();
		Inventory cInv = c.getInventory();
		Inventory inv = Bukkit.createInventory( ply, cInv.getSize(), cInv.getTitle() );
		int i = 0;
		while (i < cInv.getSize()) {
			ItemStack item = cInv.getItem(i);
			if (item != null) {
				inv.setItem(i, item);
			}
			i++;
		}
		ply.openInventory( inv );
		inventories.put(ply, b);
		return inv;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL && inVanish(e.getPlayer())) {
			e.setCancelled(true);
		}
		Block b = e.getClickedBlock();
		if( e.getAction() == Action.RIGHT_CLICK_BLOCK && ( b.getState() instanceof  Chest ) ){
			if( inVanish( e.getPlayer() ) || inventories.containsValue( e.getClickedBlock() ) ){
				e.setCancelled( true );
				openVirtualInv( e.getClickedBlock(), e.getPlayer() );
			}
		}
	}

	private Player getByInv(Block b) {
		for (Map.Entry<Player, Block> e : inventories.entrySet()) {
			if (e.getValue().equals(b)) {
				return e.getKey();
			}
		}
		return null;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (inventories.containsValue(e.getBlock())) {
			Player ply = getByInv(e.getBlock());
			if (ply != null) {
				inventories.remove(ply);
				ply.closeInventory();
			}
		}
	}

	@EventHandler
	public void onEnventoryClose(InventoryCloseEvent e) {
		if (inventories.containsKey(e.getPlayer())) {
			Block b = inventories.get(e.getPlayer());
			Chest c = (Chest) b.getState();
			Inventory inv = e.getInventory();
			Inventory cInv = c.getInventory();
			int i = 0;
			while (i < inv.getSize()) {
				ItemStack item = inv.getItem(i);
				if (item != null) {
					cInv.setItem(i, item);
				} else {
					cInv.setItem(i, new ItemStack(Material.AIR));
				}
				i++;
			}
			inventories.remove(e.getPlayer());
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<String>();
		if (sender instanceof Player) {
			Player ply = (Player) sender;
			if (!ply.hasPermission("sas.vanish.see")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!inVanish(player)) {
						list.add(player.getName());
					}
				}
			}
		}
		return list;
	}

	@Override
	public boolean run(CommandSender commandSender, Command command, String s, String[] strings) {
		if (strings.length == 1) {
			if (vanish(strings[0])) {
				unVanish(strings[0]);
			}
		} else {
			if (commandSender instanceof Player) {
				if (vanishPly((Player) commandSender)) {
					unVanishPly((Player) commandSender);
				}
			} else {
				err(commandSender, "U don't even have a body");
			}
		}
		return true;
	}

	@Override
	public List<String> tab(CommandSender commandSender, Command command, String string, String[] strings) {
		return null;
	}
}

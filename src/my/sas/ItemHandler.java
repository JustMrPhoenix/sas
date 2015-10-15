package my.sas;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import com.google.common.reflect.ClassPath;

public class ItemHandler implements Listener {

	public SasPlugin plugin;
	public Map<String, Object> items = new HashMap();
	public Map<Player, String> plys = new HashMap();
	public SasItemCommandExecutor executor = new SasItemCommandExecutor(this);

	public ItemHandler(SasPlugin plugin, ClassLoader loader) {
		this.plugin = plugin;
		plugin.getCommand("sasitem").setExecutor(executor);
		ClassPath path = null;
		try {
			path = ClassPath.from(loader);
			for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive("my.sas.items")) {
				Class<?> clazz = null;
				clazz = Class.forName(info.getName(), true, loader);
				Constructor<?> c = clazz.getConstructor(String.class);
				Object object = c.newInstance(info.getSimpleName());
				items.put(info.getSimpleName(), object);
				SasItemBase base = ( SasItemBase ) object;
				if( base.listen ){
					plugin.Manager.registerEvents( (Listener) object, plugin );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getItem(String name) {
		if (items.containsKey(name)) {
			return items.get(name);
		}
		return null;
	}

	private boolean giveTo(Object i, Player ply) {
		SasItemBase base = (SasItemBase) i;
		ply.getInventory().addItem(base.item());
		return true;
	}

	public void checkItem(Player ply, ItemStack i) {
		if (i == null) {
			plys.put(ply, "");
			return;
		}
		boolean found = false;
		for (Entry<String, Object> s : items.entrySet()) {
			try {
				SasItemBase base = (SasItemBase) s.getValue();
				if (base.match(i)) {
					if (base.hasPermission(ply)) {
						plys.put(ply, s.getKey());
						base.onEquip(ply);
						found = true;
					}
					break;
				}
			} catch (Exception ex) {
				plugin.getLogger().log(Level.WARNING, "SAS GOT ERRORS!Can't check " + ply.getDisplayName() + "'s item!");
			}
		}
		if (!found) {
			plys.put(ply, "");
		}
	}

	public SasItemBase equped(Player ply) {
		String n = plys.get(ply);
		if ( n != null && !n.equals("") && items.containsKey(n)) {
			SasItemBase base = (SasItemBase) items.get(n);
			return base;
		}
		return null;
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		checkItem(e.getPlayer(), e.getPlayer().getItemInHand());
	}

	@EventHandler
	public void onItemHeldChange(PlayerItemHeldEvent e) {
		checkItem(e.getPlayer(), e.getPlayer().getInventory().getItem(e.getNewSlot()));
	}

	@EventHandler
	public void PickupItem(PlayerPickupItemEvent e) {
		checkItem(e.getPlayer(), e.getPlayer().getItemInHand());
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		SasItemBase base = equped(e.getPlayer());
		if (base != null) {
			try {
				base.onDrop(e);
			}catch (Exception ex){
				plugin.getLogger().log( Level.WARNING, "Cant execute drop event for item "+base.id+"");
				//ex.printStackTrace();
			}
		}
	}

	@EventHandler
	public void OnEntityDamage(EntityDamageByEntityEvent e) {
		Entity ent = e.getDamager();
		if (ent instanceof Player) {
			Player ply = (Player) ent;
			SasItemBase base = equped(ply);
			if (base != null) {
				try {
					base.onDamage(e);
				}catch (Exception ex){
					plugin.getLogger().log( Level.WARNING, "Cant execute damage event for item "+base.id+"");
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		SasItemBase base = equped(e.getPlayer());
		if (base != null) {
			try {
				base.onEvent(e);
			}catch (Exception ex){
				plugin.getLogger().log( Level.WARNING, "Cant execute "+e.getAction().name()+" event for item "+base.id+"");
				//ex.printStackTrace();
			}
		}
	}
}

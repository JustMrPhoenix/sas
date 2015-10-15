package my.sas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import sun.java2d.cmm.ProfileDeferralInfo;

public class SasItemBase implements Listener{
	protected String id = "unknown";
	protected String name = "unknown";
	protected String lore = "";
	protected Material material = Material.AIR;
	protected String permission = "sas.item.unknown";
	protected boolean listen = false;

	public String getName() {
		return name;
	}

	public String getLore() {
		return lore;
	}

	public Material getMaterial() {
		return material;
	}

	public String getPermission() {
		return permission;
	}


	public boolean hasPermission(Player ply) {
		return ply.hasPermission(permission);
	}

	public void generatePermission() {
		permission = "sas.item." + name;
	}

	public void setName(String name) {
		this.name = name;
		generatePermission();
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public void set(String id, String name, String lore, Material material) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.material = material;
		generatePermission();
	}

	public ItemStack postData(ItemStack i) {
		return i;
	}

	public ItemStack item() {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		List<String> lore = new ArrayList<String>();
		lore.add("SAScripted");
		lore.add(this.lore);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return postData(item);
	}

	public boolean match(ItemStack item) {
		ItemMeta m = item.getItemMeta();
		if (item.getType().equals(material) && m.getDisplayName().equalsIgnoreCase(name) && m.getLore().get(0).equalsIgnoreCase("SAScripted") && m.getLore().get(1).equalsIgnoreCase(lore)) {
			return true;
		}
		return false;
	}

	public void onEvent(PlayerInteractEvent e) {

	}

	public void onEquip(Player ply) {

	}

	public void onDrop(PlayerDropItemEvent e) {

	}

	public void onDamage(EntityDamageByEntityEvent e) {

	}
}

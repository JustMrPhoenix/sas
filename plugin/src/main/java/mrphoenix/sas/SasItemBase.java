package mrphoenix.sas;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SasItemBase implements Listener {
    protected String id = "unknown";
    protected String name = "unknown";
    protected String lore = "";
    protected Material material = Material.AIR;
    protected String permission = "sas.item.unknown";
    protected boolean listen = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        generatePermission();
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material mat) {
        material = mat;
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
        try {
            boolean materialEquals = item.getType().equals(material);
            boolean nameEquals = m.getDisplayName().equalsIgnoreCase(name);
            boolean sasScripted = m.getLore().get(0).equalsIgnoreCase("SAScripted");
            boolean loreEquals = m.getLore().get(1).equalsIgnoreCase(lore);
            return materialEquals && nameEquals && sasScripted && loreEquals;
        } catch (NullPointerException exception) {
            return false;
        }
    }

    public void onEvent(PlayerInteractEvent e) {

    }

    public void onEquip(Player ply) {

    }

    public void onDrop(PlayerDropItemEvent e) {

    }

    public void onDamage(EntityDamageByEntityEvent e) {

    }

    public void onTimer(Player ply) {

    }
}

package my.sas.kit;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Kit {
    private HashMap<Block, String> signs = new HashMap<Block, String>();
    private Block chest;
    private String name, permission, state;

    public Kit() {
    }

    public Kit(String kitname) {
        name = kitname;
    }

    public Kit(Block nchest, Block nsign, String kitname, String npermission) {
        Block ik;
        if (nchest == null || !isChest(nchest)) {
            ik = isKitable(nsign);
            System.out.println("" + ik);
        } else {
            ik = nchest;
        }
        if (ik != null) {

            if (kitname != null) {
                name = kitname;
            } else {
                name = getLines(nsign)[1];
            }

            if (npermission != null && !permission.equalsIgnoreCase("")) {
                permission = npermission;
            } else {
                permission = "kit." + name;
            }
            if (nsign != null) {
                addSign(nsign, permission);
            }

            chest = ik;

            state = "ready";
        }
    }

    public Block getChest() {
        return chest;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public HashMap<Block, String> getSigns() {
        return signs;
    }

    private void setPermission(String p) {
        permission = p;
    }

    public void setName(String n) {
        name = n;
    }

    public boolean setState(String newstate) {
        if (
                newstate == null ||
                        newstate.equalsIgnoreCase("") ||
                        chest == null ||
                        permission == null ||
                        permission.equalsIgnoreCase("") ||
                        name == null ||
                        name.equalsIgnoreCase("")
                ) {
            return false;
        }
        state = newstate;
        return state == newstate;
    }

    private String[] getLines(Block s) {
        if (s.getState() instanceof Sign) {
            Sign si = (Sign) s.getState();
            return si.getLines();
        } else {
            return null;
        }
    }

    public boolean hasPermission(Player ply, Block sign) {
        if (ply == null) {
            return false;
        }
        if (sign != null && hasSign(sign)) {
            String perm = signs.get(sign);
            if (perm.equalsIgnoreCase("*")) {
                return true;
            }
            return ply.hasPermission(signs.get(sign));
        }
        return ply.hasPermission(permission);
    }

    private boolean isChest(Block b) {
        if (b != null && (b.getType() == Material.CHEST || b.getType() == Material.TRAPPED_CHEST)) {
            return true;
        }
        return false;
    }

    public boolean setChest(Block nchest) {
        if (isChest(nchest)) {
            chest = nchest;
            return true;
        } else {
            return false;
        }
    }

    private Block isKitable(Block sign) {
        if (sign != null && (sign.getType() == Material.CHEST || sign.getType() == Material.TRAPPED_CHEST)) {
            return sign;
        }
        if (sign == null || sign.getType() != Material.WALL_SIGN) {
            return null;
        }
        Location loc = sign.getLocation();
        World world = loc.getWorld();

        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        switch (sign.getData()) {
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

        if (isChest(block)) {
            return block;
        } else {
            return null;
        }
    }

    public void giveTo(Player ply) {
        if (ply != null) {
            Chunk chunk = chest.getChunk();
            System.out.println("Giving "+name+" kit to " + ply.getName());
            boolean unload = false;
            if (!chunk.isLoaded()) {
                chunk.load();
                unload = true;
            }
            Chest c = (Chest) chest.getState();
            Inventory chestInv = c.getInventory();
            Inventory plyInv = ply.getInventory();
            for (ItemStack is : chestInv.getContents()) {
                if (is == null || is.getType() == Material.AIR) {
                    continue;
                }
                plyInv.addItem(is);
            }
            if (unload) {
                chunk.unload();
            }
        }
    }


    public boolean hasSign(Block csign) {
        return signs.containsKey(csign);
    }


    public boolean addSign(Block nsign, String perm) {
        if (!hasSign(nsign) && nsign.getType() == Material.WALL_SIGN) {
            if (perm == null) {
                signs.put(nsign, permission);
            } else {
                signs.put(nsign, perm);
            }
            return true;
        }
        return false;
    }

    public boolean removeSign(Block nsign) {
        if (signs.containsKey(nsign)) {
            signs.remove(nsign);
            return true;
        }
        return false;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap();
        data.put("chest_pos", chest.getLocation().serialize());
        data.put("permission", permission);
        Map<String, Object> kitsloc = new HashMap();
        int index = 1;
        for (Entry<Block, String> e : signs.entrySet()) {
            Map<String, Object> loc = e.getKey().getLocation().serialize();
            loc.put("permission", e.getValue());
            kitsloc.put(index + "", loc);
            index = index + 1;
        }
        data.put("signs", kitsloc);
        return data;
    }

    public static Kit deserialize(Map<String, Object> data, String kitname) {
        Kit kit = new Kit(kitname);
        MemorySection chest_pos = (MemorySection) data.get("chest_pos");
        kit.setChest(Location.deserialize(chest_pos.getValues(false)).getBlock());
        kit.setPermission((String) data.get("permission"));
        MemorySection kitlist = (MemorySection) data.get("signs");
        for (Entry<String, Object> e : kitlist.getValues(false).entrySet()) {
            MemorySection sec = (MemorySection) e.getValue();
            Location loc = Location.deserialize(sec.getValues(false));
            kit.addSign(loc.getBlock(), sec.getString("permission"));
        }

        return kit;
    }

    public void remove() {
        state = "removed";
        chest = null;
        signs.clear();
        if (isChest(chest)) {
            chest.breakNaturally();
        }
    }
}

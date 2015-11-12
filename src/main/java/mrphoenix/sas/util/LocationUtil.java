package mrphoenix.sas.util;

import mrphoenix.sas.geom.Point;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class LocationUtil {
    public static String formatWithColor(Location location) {
        return "X:" +
                ChatColor.GREEN +
                location.getBlockX() +
                ChatColor.RESET +
                ", Y:" +
                ChatColor.GREEN +
                location.getBlockY() +
                ChatColor.RESET +
                ", Z:" +
                ChatColor.GREEN +
                location.getBlockZ();
    }

    public static Point toPoint(Location location) {
        return new Point(location.getX(), location.getZ());
    }

    public static Location getHighestLocation(World world, Point point) {
        return new Location(world, point.getX(), world.getHighestBlockYAt(point.getIntX(), point.getIntY()), point.getY());
    }

    public static boolean isSafe(Location location) {
        Location locationAbove = location.clone().add(new Vector(0, 1, 0));
        return isBlockTypeAir(location) && isBlockTypeAir(locationAbove);
    }

    public static boolean isBlockTypeAir(Location location) {
        return location.getBlock().getType() == Material.AIR;
    }
}

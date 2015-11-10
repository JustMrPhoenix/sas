package my.sas.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class LocationUtil {
    public static String format(Location location) {
        return "X:" + location.getBlockX() + ",Y:" + location.getBlockY() + ChatColor.GREEN + ",Z:" + ChatColor.DARK_BLUE + location.getBlockZ();
    }
}

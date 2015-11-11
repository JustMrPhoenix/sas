package my.sas.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;

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
}

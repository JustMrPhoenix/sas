package mrphoenix.sas.commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import mrphoenix.sas.SasCommandBase;
import mrphoenix.sas.SasPlugin;
import mrphoenix.sas.geom.Disk;
import mrphoenix.sas.geom.Point;
import mrphoenix.sas.util.LocationUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SasWild extends SasCommandBase {
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_RADIUS = 1500;
    private static final double MAX_RADIUS = 6000;

    private WorldGuardPlugin worldGuard;
    private Disk spawnDisk;

    public SasWild(SasPlugin plugin) {
        super(plugin, "wild");

        this.worldGuard = plugin.getWorldGuard();

        Point spawn = LocationUtil.toPoint(defaultWorld.getSpawnLocation());
        this.spawnDisk = new Disk(spawn, MIN_RADIUS, MAX_RADIUS);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            tryTeleportToWild(player, defaultWorld);
            return true;
        } else {
            plugin.getLogger().warning("Only in-game players can use /wild.");
            return true;
        }
    }

    private void tryTeleportToWild(Player player, World world) {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            Point randomSpawnDiskPoint = spawnDisk.getRandomPoint();
            Location highestLocation = LocationUtil.getHighestLocation(world, randomSpawnDiskPoint);

            if (LocationUtil.isSafe(highestLocation)) {
                player.teleport(highestLocation);
                informResultSuccessful(player, highestLocation);
                break;
            }
        }
    }

    private void informResultSuccessful(Player player, Location location) {
        player.sendMessage(
                ChatColor.GOLD      + "Телепортирование на" +
                ChatColor.DARK_AQUA + " X: " +
                ChatColor.GOLD      + location.getX() +
                ChatColor.DARK_AQUA + " Z: " +
                ChatColor.GOLD      + location.getZ());
    }
}

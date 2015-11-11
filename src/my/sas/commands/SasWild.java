package my.sas.commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import my.sas.SasCommandBase;
import my.sas.SasPlugin;
import my.sas.util.Point;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SasWild extends SasCommandBase {
    private WorldGuardPlugin worldGuard;

    public SasWild(SasPlugin plugin) {
        super(plugin, "wild");
        this.worldGuard = plugin.getWorldGuard();
    }

    public boolean checkLocation(Location location) {
        return location.getBlock().getType() == null || location.getBlock().getType() == Material.AIR;
    }

    public Location getRandomLocation(World world, Point min, Point max) {
        Random random = new Random();

        int x = random.nextInt(max.getX() - min.getX()) + min.getX();
        int z = random.nextInt(max.getY() - min.getY()) + min.getY();

        int y = Bukkit.getServer().getWorld("world").getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }

    private void tpToRandomPos(Player player, int iteration) {
        if (iteration >= 10) {
            player.sendMessage(ChatColor.RED + "Достигнуто максимальное количество попыток!");
            return;
        }
        Location spawn = Bukkit.getServer().getWorld("world").getSpawnLocation();
        int spawn_x = spawn.getBlockX();
        int spawn_z = spawn.getBlockZ();
        Point min = new Point(spawn_x - 6000, spawn_z - 6000);
        Point max = new Point(spawn_x + 6000, spawn_z + 6000);
        Location loc = getRandomLocation(player.getWorld(), min, max);
        Chunk c = loc.getChunk();
        boolean loaded = c.isLoaded();
        if (!loaded) {
            c.load();
        }
        if (!checkLocation(loc) || !worldGuard.canBuild(player, loc) || spawn.distance(loc) < 1500) {
            player.sendMessage(ChatColor.RED + "Безопасное место для телепорта не найдено. Пробуем ещё раз...");
            tpToRandomPos(player, iteration + 1);
            if (!loaded) {
                c.unload();
            }
        } else {
            player.teleport(loc);
            player.sendMessage(ChatColor.DARK_AQUA + "Телерортирование на: X: " + ChatColor.GOLD + loc.getX() + ChatColor.DARK_AQUA + " Z: " + ChatColor.GOLD + loc.getZ());
        }
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            plugin.getLogger().warning("RLY?");
            return true;
        }
        Player player = (Player) commandSender;
        tpToRandomPos(player, 0);
        return true;
    }
}

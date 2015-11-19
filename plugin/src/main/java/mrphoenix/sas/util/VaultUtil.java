package mrphoenix.sas.util;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;


public class VaultUtil {
    public static Permission getPermissionsProvider() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        return rsp.getProvider();
    }
}

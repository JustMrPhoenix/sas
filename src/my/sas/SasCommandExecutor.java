package my.sas;

import com.google.common.reflect.ClassPath;
import org.bukkit.command.CommandExecutor;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Mr.Phoenix on 9/13/2015.
 */
public class SasCommandExecutor {

    public SasPlugin plugin;

    public SasCommandExecutor(SasPlugin plugin, ClassLoader loader){
		this.plugin = plugin;
		ClassPath path = null;
		try {
			path = ClassPath.from(loader);
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (ClassPath.ClassInfo info : path.getTopLevelClassesRecursive( "my.sas.commands" )) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(info.getName(), true, loader );
				Constructor<?> c = clazz.getConstructor( SasPlugin.class );
				Object object = c.newInstance(plugin);
                SasCommandBase base =( SasCommandBase ) object;
				plugin.getLogger().info( base+"|"+base.getCommand() );
				plugin.getCommand( base.getCommand( ) ).setExecutor( base );
                plugin.Manager.registerEvents( base, plugin );
			} catch (SecurityException | InstantiationException | IllegalAccessException | NoSuchMethodException | IllegalArgumentException | ClassNotFoundException | InvocationTargetException e) {
				e.printStackTrace();
			}
        }
	}
}

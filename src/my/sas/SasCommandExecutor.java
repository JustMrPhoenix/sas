package my.sas;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class SasCommandExecutor {

    public SasPlugin plugin;
	public HashMap<String,SasCommandBase> commands = new HashMap<String,SasCommandBase>();

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
				commands.put( base.getCommand(), base );
				plugin.getLogger().info( base+"|"+base.getCommand() );
				plugin.getCommand( base.getCommand( ) ).setExecutor( base );
                plugin.pluginManager.registerEvents( base, plugin );
			} catch ( Exception e) {
				e.printStackTrace();
			}
        }
	}
}

package my.sas;

import com.earth2me.essentials.Essentials;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import java.io.File;
import java.io.IOException;

public class SasPlugin extends JavaPlugin implements Listener {
	public FileConfiguration mainConfig;
	public File mainConfigFile;
	public FileConfiguration config;
	public PluginManager Manager;
	public KitExecutor kitExecutor;
	public InvKeeper invKeeper;
	public ItemHandler itemHandler;
	public SasCommandExecutor sasCommandExecuter;
	public AntiGodKill antiGodKill;
	public Essentials ess;
	public WorldGuardPlugin wg;
	public SasCommandBlock sasCommandBlock;

	public FileConfiguration config(){
		mainConfigFile = new File(getDataFolder(), "db.yml");
		if (!mainConfigFile.exists()) {
			try {
				mainConfigFile.createNewFile();
			} catch (IOException e) {
				getLogger().info("Failed to create new configuration file.");
				e.printStackTrace();
			}
		}
		FileConfiguration myFileConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
		System.out.println( myFileConfig );
		return myFileConfig;
	}

	@Override
	public void onEnable()
	{
		config = getConfig();
		mainConfig = config();
		Manager = getServer( ).getPluginManager();
		ess = (Essentials) Manager.getPlugin("Essentials");
		wg = (WorldGuardPlugin) Manager.getPlugin("WorldGuard");
		kitExecutor = new KitExecutor( this );
		invKeeper = new InvKeeper( );
		itemHandler = new ItemHandler( this, this.getClassLoader( ) );
		sasCommandExecuter = new SasCommandExecutor( this, this.getClassLoader() );
		antiGodKill = new AntiGodKill( ess );
		sasCommandBlock = new SasCommandBlock( this );
		Manager.registerEvents( this, this);
		Manager.registerEvents( invKeeper, this );
		Manager.registerEvents( kitExecutor, this );
		Manager.registerEvents( itemHandler, this );
		Manager.registerEvents( antiGodKill, this );
		Manager.registerEvents( sasCommandBlock, this);
		getLogger().info( "SAS plugin activated" );
	}
	
	@Override
	public void onDisable( )
	{
		System.out.println(mainConfig.toString());
		kitExecutor.save();
		try {
			mainConfig.save(mainConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getLogger().info( "SAS plugin deactivated" );
	}
}
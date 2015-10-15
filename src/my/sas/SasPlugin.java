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
	public FileConfiguration main_config;
	public File main_config_file;
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

	public FileConfiguration config( ){
		//Get/Create File
		main_config_file = new File(getDataFolder(), "db.yml");
		if (!main_config_file.exists()) {
			try {
				main_config_file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileConfiguration myFileConfig = YamlConfiguration.loadConfiguration(main_config_file);
		System.out.println( myFileConfig );
		return myFileConfig;
	}
	//Nothing special here.U can leave
	@Override
	public void onEnable()
	{
		config = getConfig();
		main_config = config();
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
		System.out.println(main_config.toString());
		kitExecutor.save();
		try {
			main_config.save( main_config_file );
		} catch (IOException e) {
			e.printStackTrace();
		}
		getLogger().info( "SAS plugin deactivated" );
	}
}
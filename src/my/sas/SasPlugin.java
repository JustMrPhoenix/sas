package my.sas;

import com.earth2me.essentials.Essentials;
import my.sas.kit.KitExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class SasPlugin extends JavaPlugin implements Listener {
	public SasConfig main_config;
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

	//Nothing special here.U can leave
	@Override
	public void onEnable()
	{
		config = getConfig();
		main_config = new SasConfig( "db.yml", this );
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
		main_config.setAutosave( true );
	}
	
	@Override
	public void onDisable( )
	{
		System.out.println(main_config.toString());
		main_config.save( );
		getLogger().info( "SAS plugin deactivated" );
	}
}
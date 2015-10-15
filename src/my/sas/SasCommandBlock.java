package my.sas;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Phoenix on 9/14/2015.
 */
public class SasCommandBlock implements Listener, CommandExecutor{
    private SasPlugin plugin;
    private Map<String,Integer> blocks = new HashMap<String,Integer>();
    private ConfigurationSection config;
    private Map<String,Map<String,Long>> users = new HashMap<String,Map<String,Long>>();

    public SasCommandBlock( SasPlugin plugin ){
        this.plugin = plugin;
        plugin.getCommand( "sasblock" ).setExecutor( this );
        plugin.getCommand( "sasunblock" ).setExecutor( this );
        FileConfiguration fileConfiguration = plugin.main_config;
        if( plugin.main_config.contains( "blocks" ) )
        {
            config = plugin.main_config.getConfigurationSection( "blocks" );
            Map<String,Object> data = config.getValues( false );
            deserialize( data );
        }
    }

    public Map<String,Object> serialize( ){
        Map<String,Object> map = new HashMap<String,Object>();
        for( Map.Entry<String,Integer> e : blocks.entrySet() ){
            map.put( e.getKey(), e.getValue().toString() );
        }
        return map;
    }

    public void save( )
    {
        Map<String,Object> map = serialize( );
        if( map != null )
        {
            plugin.main_config.set("blocks", map);
            plugin.saveConfig();
        }
    }

    public void deserialize( Map<String,Object> data )
    {
        for( Map.Entry<String,Object> e : data.entrySet( ) )
        {
            blocks.put( e.getKey(), Integer.parseInt( (String) e.getValue() ) );
        }
    }
    public boolean checkCommand( String cmd, Player ply ){
        if( blocks.containsKey( cmd ) && !ply.hasPermission( "sas.block.ignore" ) ){
            int delay = blocks.get(cmd);
            Long time = System.currentTimeMillis() / 1000L;
            if( !users.containsKey( ply.getName() ) ){
                Map<String,Long> map = new HashMap<String,Long>();
                users.put( ply.getName(), map );
            }
            Map<String,Long> map = users.get( ply.getName() );
            if( map.containsKey( cmd ) ){
                if( map.get( cmd ) > time-delay ){
                    plugin.getLogger().info("Cooldown :)");
                    ply.sendMessage(ChatColor.RED + "Команда будет доступна через "+( delay-( time - map.get( cmd ) ) )+" секунд!");
                    return false;
                }else{
                    map.put(cmd, time);
                }
            }else{
                map.put( cmd, time );
            }
            users.put(ply.getName(), map );
        }
        return true;
    }

    public void checkEvent( PlayerCommandPreprocessEvent e){
        Player ply = e.getPlayer();
        String cmd = e.getMessage().substring(1);
        if( !checkCommand( cmd, ply ) ){
            e.setCancelled( true );
        }
    }

    @EventHandler( priority = EventPriority.HIGHEST )
    public void onPreprocess(PlayerCommandPreprocessEvent e) {
        checkEvent( e );
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (command.getName( )){
            case "sasblock":
                if( strings.length == 2){
                    if( !blocks.containsKey( strings[0] ) ){
                        blocks.put(strings[0], Integer.parseInt(strings[1]));
                        if( commandSender instanceof Player ){
                            ( ( Player ) commandSender ).sendMessage( ChatColor.GREEN+"Добавлено!" );
                        }else{
                            plugin.getLogger().info( "Добалвено" );
                        }
                    }else{
                        if( commandSender instanceof Player ){
                            ( ( Player ) commandSender ).sendMessage( ChatColor.RED+"Не добавлено!" );
                        }else{
                            plugin.getLogger().info( "Не добалвено" );
                        }
                    }
                    return true;
                }else if(strings.length == 1){
                    if( blocks.containsKey( strings[0] ) ){
                        String str = blocks.get( strings[0] )+"s";
                        if( commandSender instanceof Player ){
                            ( ( Player ) commandSender ).sendMessage( str );
                        }else{
                            plugin.getLogger().info( str );
                        }
                    }
                    return true;
                }else if( strings.length == 0){
                    String str = "Blocks\n";
                    for(Map.Entry< String, Integer> e: blocks.entrySet() ){
                        str = str+"\n"+e.getKey()+" - "+e.getValue();
                    }
                    if( commandSender instanceof Player ){
                        ( ( Player ) commandSender ).sendMessage( str );
                    }else{
                        plugin.getLogger().info( str );
                    }
                    return true;
                }
                break;
            case "sasunblock":
                if( strings.length == 1){
                    if( blocks.containsKey( strings[0] ) ){
                        blocks.remove( strings[0] );
                        save( );
                    }
                    return true;
                }
                break;
        }
        save( );
        return false;
    }
}

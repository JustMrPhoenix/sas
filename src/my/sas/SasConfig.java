package my.sas;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SasConfig {
    private File file;
    private String name;
    private FileConfiguration fileConfiguration;
    private SasPlugin plugin;
    private boolean autosave = false;
    private Timer autosaveTimer;
    private TimerTask autosaveTask;

    public SasConfig(String config_name, SasPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), config_name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigurationSection getSection( String name ){
        return fileConfiguration.getConfigurationSection( name );
    }

    public boolean isAutosave() {
        return autosave;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void set(String path, Object value) {
        fileConfiguration.set(path, value);
        if (autosave) {
            save();
        }
    }

    public Object get(String path) {
        return fileConfiguration.get(path);
    }


    public void setAutosave(boolean autosave) {
        this.autosave = autosave;
        if( autosave ) {
            if( autosaveTimer == null ) {
                autosaveTimer = new Timer();
            }
            if( autosaveTask == null ) {
                autosaveTask = new TimerTask() {
                    @Override
                    public void run() {
                        save();
                    }
                };
            }
            autosaveTimer.schedule(autosaveTask, 20000, 20000);
        }else{
            autosaveTimer.cancel();
            autosaveTimer.purge();
        }
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

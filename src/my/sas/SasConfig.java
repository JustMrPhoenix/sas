package my.sas;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SasConfig{
	private File file;
	private String name;
	private FileConfiguration fileConfiguration;
	private SasPlugin plugin;

	public SasConfig( String config_name, SasPlugin plugin ){
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), "db.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
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
}

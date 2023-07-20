package rd.dru;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import rd.dru.config.Config;
import rd.dru.nms.NMSHandler;
import rd.dru.nms.VersionChecker;
import rd.dru.thread.SafeThread;

import java.io.File;

/**
 * 
 * @author Dru_TNT
 *
 */
public class SuperHarvest extends JavaPlugin{
	private static SuperHarvest inst;
	public static SafeThread thread;
	private static Config config;
	public static NMSHandler nms;
	@Override
	public void onEnable() {
		inst = this;
		config = new Config(new File("plugins/SuperHarvest/config.yml"));
		nms = VersionChecker.getCurrentVersion().getNMS();
		thread = new SafeThread(config.bufferMs);
		thread.start();
		new EventManager();
		getCommand("superharvest").setExecutor(new Commands());
		
		//int pluginId = 15675; // <-- Replace with the id of your plugin!
        //new Metrics(this, pluginId);
    	Bukkit.getLogger().info("SuperHarvest is enabled.");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getLogger().info("SuperHarvest is disabled.");
	}
	
	public static SuperHarvest getInstance() {
		return inst;
	}
	
	public static Config getSuperConfig() {
		return config;
	}
}

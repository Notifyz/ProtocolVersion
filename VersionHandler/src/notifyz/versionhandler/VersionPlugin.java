package notifyz.versionhandler;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import lombok.Setter;
import notifyz.versionhandler.commands.ProtocolCommand;

/**
 *
 * @author Notifyz
 * This is the main class for VersionHandler.
 */

@Getter
@Setter
public class VersionPlugin extends JavaPlugin {

	public VersionPlugin instance;

	/**
	 * @param Enabling plugin.
	 */
	@Override
	public void onEnable() {
		instance = this;
		getCommand("protocol").setExecutor(new ProtocolCommand());
	}

	/**
	 * @param Disabling plugin. (This is completely unnecessary but I will keep it)
	 */
	@Override
	public void onDisable() {
		instance = null;
	}
}

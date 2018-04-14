package notifyz.versionhandler.internal;

import org.bukkit.entity.Player;

/**
 *
 * @author Notifyz
 */
public interface PlayerVersion {

	/**
	 *
	 * @param Used to get player's version.
	 * @return Client version of player, default 5 (1.7.10)
	 */
	public static int getClientVersion(Player player) {
		try {
			Object handle = null, connection = null, networkManager;
			return (int) (networkManager = (connection = (handle = player.getClass().getMethod("getHandle").invoke(player)).getClass().getDeclaredField("playerConnection").get(handle)).getClass().getDeclaredField("networkManager").get(connection)).getClass().getDeclaredMethod("getVersion").invoke(networkManager);
		} catch (ReflectiveOperationException exception) {
			exception.printStackTrace();
		}
		return 5;
	}

}

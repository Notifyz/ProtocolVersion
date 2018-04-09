package notifyz.versionhandler.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ProtocolCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("protocol.check")) {
			sender.sendMessage(ChatColor.RED + "You don't have enough permission for this.");
			return true;
		}
		if (args.length == 0 || args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <player>");
			return true;
		}

		Player player = Bukkit.getPlayer(args[0]);

		if (player == null) {
			sender.sendMessage(ChatColor.RED + "Player not found.");
			return true;
		}

		String protocolName;

		switch (((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion()) {
		case 4:
			protocolName = "1.7.2";
			break;

		case 5:
			protocolName = "1.7.10";
			break;

		case 47:
			protocolName = "1.8";
			break;

		default:
			protocolName = "Unknown";
			break;
		}

		sender.sendMessage(ChatColor.GOLD + "Player '" + ChatColor.WHITE + player.getName() + ChatColor.GOLD + "' is using protocol (" + ChatColor.WHITE + protocolName + ChatColor.GOLD + ')');
		return false;
	}

}

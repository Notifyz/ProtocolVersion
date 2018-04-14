package notifyz.versionhandler.commands;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import notifyz.versionhandler.internal.PlayerVersion;

/**
 *
 * @author Notifyz
 * This is the command class.
 */
public class ProtocolCommand implements CommandExecutor {

	private static final Map<Integer, String> VERSION_MAPPINGS;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!sender.hasPermission("protocol.check")) {
			sender.sendMessage(ChatColor.RED + "You don't have enough permission for this.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " check|list <player|protocol>");
			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {

			Map<Player, Integer> versions = new HashMap<>();
			Bukkit.getOnlinePlayers().forEach(next -> {
				versions.put(next, PlayerVersion.getClientVersion(next));
			});
			if (args.length > 1) {
				String[] targets = new String[args.length - 1];
				System.arraycopy(args, 1, targets, 0, targets.length);
				for (String next : targets) {
					if (next.matches("\\d+")) {

						int version = Integer.valueOf(next);
						if (!VERSION_MAPPINGS.containsKey(version)) {
							sender.sendMessage(ChatColor.RED + "Valid protocols: " + VERSION_MAPPINGS.keySet());
							break;
						}

						List<String> names = versions.keySet().stream().filter(player -> versions.get(player) == version).map(Player::getName).collect(Collectors.toList());
						sender.sendMessage(ChatColor.GOLD + "[" + VERSION_MAPPINGS.get(version) + "] (" + ChatColor.GOLD + names.size() + "): " + ChatColor.WHITE + (names.size() > 0 ? String.join(", ", names) : "<none>"));
					} else {
						sender.sendMessage(ChatColor.RED + "Invalid number '" + next + "'!");
						break;
					}
				}
				return true;
			}

			for (int version : VERSION_MAPPINGS.keySet()) {
				List<String> names = versions.keySet().stream().filter(player -> versions.get(player) == version).map(Player::getName).collect(Collectors.toList());
				sender.sendMessage(ChatColor.GOLD + "[" + VERSION_MAPPINGS.get(version) + "] (" + ChatColor.GOLD + names.size() + "): " + ChatColor.WHITE + (names.size() > 0 ? String.join(", ", names) : "<none>"));
			}
			return true;
		}

		if (args[0].equalsIgnoreCase("check")) {

			Player player = Bukkit.getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage(ChatColor.RED + "Player not found.");
				return true;
			}

			int protocol;
			sender.sendMessage(ChatColor.GOLD + "Player '" + ChatColor.WHITE + player.getName() + ChatColor.GOLD + "' is using protocol " + (protocol = PlayerVersion.getClientVersion(player)) + " (" + ChatColor.WHITE + VERSION_MAPPINGS.getOrDefault(protocol, "Unknown") + ChatColor.GOLD + ')');
		}
		return false;
	}

	static {
		VERSION_MAPPINGS = new LinkedHashMap<>();
		VERSION_MAPPINGS.put(47, "1.8");
		VERSION_MAPPINGS.put(5, "1.7.10");
		VERSION_MAPPINGS.put(4, "1.7.2");
	}
}

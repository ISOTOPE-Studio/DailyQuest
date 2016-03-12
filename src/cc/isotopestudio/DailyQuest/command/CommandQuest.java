package cc.isotopestudio.DailyQuest.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.DailyQuest.DailyQuest;

public class CommandQuest implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("quest")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(DailyQuest.prefix + "必须要玩家才能执行");
				return true;
			}
		}
		return false;
	}
}
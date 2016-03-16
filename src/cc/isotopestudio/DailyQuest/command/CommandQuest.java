package cc.isotopestudio.DailyQuest.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class CommandQuest implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("quest")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(DailyQuest.prefix + "必须要玩家才能执行");
				return true;
			}
			if (args.length != 0 || !args[0].equalsIgnoreCase("help")) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("accept")) {
					if(PlayerData.getStage(player)>=4){
						player.sendMessage("已完成");
						return true;
					}
					if(PlayerData.getStage(player)>=1){
						player.sendMessage("正在进行");
						return true;
					}
					PlayerData.setStage(player, 1);
					player.sendMessage("任务开始");
					return true;
				}
				if (args[0].equalsIgnoreCase("info")) {
					player.sendMessage("任务信息");
					return true;
				}

			} else {
				return true;
			}
		}
		return false;
	}
}
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
				sender.sendMessage(DailyQuest.prefix + "����Ҫ��Ҳ���ִ��");
				return true;
			}
			if (args.length != 0 || !args[0].equalsIgnoreCase("help")) {
				Player player = (Player) sender;
				if (args[0].equalsIgnoreCase("accept")) {
					if(PlayerData.getStage(player)>=4){
						player.sendMessage("�����");
						return true;
					}
					if(PlayerData.getStage(player)>=1){
						player.sendMessage("���ڽ���");
						return true;
					}
					PlayerData.setStage(player, 1);
					player.sendMessage("����ʼ");
					return true;
				}
				if (args[0].equalsIgnoreCase("info")) {
					player.sendMessage("������Ϣ");
					return true;
				}

			} else {
				return true;
			}
		}
		return false;
	}
}
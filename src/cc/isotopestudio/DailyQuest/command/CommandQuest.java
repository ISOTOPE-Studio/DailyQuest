package cc.isotopestudio.DailyQuest.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cc.isotopestudio.DailyQuest.DailyQuest;
import cc.isotopestudio.DailyQuest.data.GlobalData;
import cc.isotopestudio.DailyQuest.data.PlayerData;

public class CommandQuest implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("quest")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.RED).append("����Ҫ��Ҳ���ִ��").toString());
				return true;
			}
			Player player = (Player) sender;
			if (args.length > 0 && !args[0].equalsIgnoreCase("help")) {
				if (args[0].equalsIgnoreCase("accept")) {
					if (!PlayerData.canAccept(player)) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
								.append("�㲻���ٽ����µ�������").toString());
						return true;
					}
					if (PlayerData.getStage(player) >= 1) {
						player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.BLUE).append("�������ڽ���")
								.toString());
						return true;
					}
					PlayerData.setStage(player, 1);
					player.sendMessage(
							new StringBuilder(DailyQuest.prefix).append(ChatColor.YELLOW).append("�������ڿ�ʼ��").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.BLUE).append("������е���һ�׶�").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
							.append("ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1Type().toString())
							.toString());
					return true;
				}
				if (args[0].equalsIgnoreCase("info")) {
					player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.AQUA)
							.append("==== ������Ϣ ====").toString());
					player.sendMessage(
							new StringBuilder().append(ChatColor.YELLOW)
									.append("�������������" + PlayerData.getTimes(player) + "�Σ���������"
											+ (PlayerData.getLimit(player) - PlayerData.getTimes(player)) + "��")
							.toString());
					if (PlayerData.canAccept(player))
						switch (PlayerData.getStage(player)) {
						case (0): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("����δ��ʼ").toString());
							break;
						}
						case (1): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е���һ�׶�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
									"ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1Type().toString())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ֻ").toString());
							break;
						}
						case (2): {
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е��ڶ��׶�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED).append(
									"�� " + GlobalData.getStage2Limit() + " ��" + GlobalData.getStage2Type().toString())
									.toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ��").toString());
							break;
						}
						case (3): {
							Location loc = GlobalData.getStage3Location(PlayerData.getStep(player));
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.BLUE).append("������е������׶�").toString());
							player.sendMessage(
									new StringBuilder("    ").append(ChatColor.RED).append("���� 10 ��ָ���ص�").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("������� " + PlayerData.getStep(player) + " ��").toString());
							player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
									.append("��һ���ص㣺X " + loc.getBlockX() + " Z " + loc.getBlockZ()).toString());
							break;
						}
						}
					else {
						player.sendMessage(new StringBuilder("").append(ChatColor.RED).append("�����ٽ����µ�������").toString());
					}
					return true;
				}
				if (args[0].equalsIgnoreCase("today")) {
					player.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.GOLD)
							.append("==== �������� ====").toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
							.append("ɱ�� " + GlobalData.getStage1Limit() + " ֻ" + GlobalData.getStage1Type().toString())
							.toString());
					player.sendMessage(new StringBuilder("    ").append(ChatColor.RED)
							.append("�� " + GlobalData.getStage2Limit() + " ��" + GlobalData.getStage2Type().toString())
							.toString());
					player.sendMessage(
							new StringBuilder("    ").append(ChatColor.RED).append("���� 10 ��ָ���ص�").toString());

					return true;
				}
				sender.sendMessage(new StringBuilder(DailyQuest.prefix).append(ChatColor.RED)
						.append("δ֪��������� /renwu �鿴����").toString());
				return true;

			} else {
				player.sendMessage(
						new StringBuilder(DailyQuest.prefix).append(ChatColor.AQUA).append("==== ���� ====").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GOLD).append("/renwu accept").append(ChatColor.GRAY)
								.append(" - ").append(ChatColor.LIGHT_PURPLE).append("��ʼÿ������").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GOLD).append("/renwu info").append(ChatColor.GRAY)
								.append(" - ").append(ChatColor.LIGHT_PURPLE).append("�鿴���������Ϣ").toString());
				sender.sendMessage(
						new StringBuilder().append(ChatColor.GOLD).append("/renwu today").append(ChatColor.GRAY)
								.append(" - ").append(ChatColor.LIGHT_PURPLE).append("�鿴����������Ϣ").toString());
				return true;
			}
		}
		return false;
	}
}